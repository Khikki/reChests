from __future__ import annotations

import argparse
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parent.parent
CHEST_ROOT = ROOT / "src" / "main" / "resources" / "assets" / "rechests" / "textures" / "entity" / "chest"
OUTPUT_ROOT = ROOT / "tools" / "renders"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Render a simple isometric preview from a 1.7.10 single chest atlas."
    )
    parser.add_argument("--atlas", required=True, help="Chest atlas path or file name.")
    parser.add_argument("--name", required=True, help="Output file name without extension.")
    parser.add_argument("--scale", type=int, default=4, help="Render scale multiplier.")
    parser.add_argument("--output-dir", default=str(OUTPUT_ROOT), help="Output directory.")
    return parser.parse_args()


def resolve_atlas(path_str: str) -> Path:
    path = Path(path_str)
    if path.is_file():
        return path

    candidate = CHEST_ROOT / path_str
    if candidate.is_file():
        return candidate

    raise FileNotFoundError(f"Chest atlas not found: {path_str}")


def crop(image: Image.Image, x: int, y: int, w: int, h: int) -> Image.Image:
    return image.crop((x, y, x + w, y + h)).convert("RGBA")


def scale_nearest(image: Image.Image, scale: int) -> Image.Image:
    return image.resize((image.width * scale, image.height * scale), Image.NEAREST)


def transform_top(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w * 2, h),
        Image.AFFINE,
        (1, -1, 0, 0.5, 0.5, 0),
        resample=Image.NEAREST,
    )


def transform_left(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w, h + w // 2),
        Image.AFFINE,
        (1, 0, 0, -0.5, 0.5, w // 2),
        resample=Image.NEAREST,
    )


def transform_right(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w, h + w // 2),
        Image.AFFINE,
        (1, 0, 0, 0.5, 0.5, 0),
        resample=Image.NEAREST,
    )


def darken(image: Image.Image, factor: float) -> Image.Image:
    result = Image.new("RGBA", image.size)
    pixels = result.load()
    src = image.load()

    for y in range(image.height):
        for x in range(image.width):
            r, g, b, a = src[x, y]
            pixels[x, y] = (int(r * factor), int(g * factor), int(b * factor), a)

    return result


def build_chest_faces(atlas: Image.Image, scale: int) -> dict[str, Image.Image]:
    # 1.7.10 single chest atlas layout.
    lid_top = scale_nearest(crop(atlas, 14, 0, 14, 14), scale)
    lid_side = scale_nearest(crop(atlas, 0, 14, 14, 5), scale)
    lid_front = scale_nearest(crop(atlas, 14, 14, 14, 5), scale)

    base_side = scale_nearest(crop(atlas, 0, 33, 14, 10), scale)
    base_front = scale_nearest(crop(atlas, 14, 33, 14, 10), scale)

    # Small latch piece near the atlas origin. Good enough for preview icons.
    latch = scale_nearest(crop(atlas, 0, 0, 4, 4), scale)

    return {
        "lid_top": transform_top(lid_top),
        "lid_left": transform_left(darken(lid_side, 0.85)),
        "lid_right": transform_right(darken(lid_front, 0.72)),
        "base_left": transform_left(darken(base_side, 0.85)),
        "base_right": transform_right(darken(base_front, 0.72)),
        "latch": transform_right(latch),
        "lid_height": lid_side.height,
        "base_height": base_side.height,
        "face_width": base_front.width,
    }


def render_chest(atlas: Image.Image, scale: int) -> Image.Image:
    faces = build_chest_faces(atlas, scale)

    top = faces["lid_top"]
    lid_left = faces["lid_left"]
    lid_right = faces["lid_right"]
    base_left = faces["base_left"]
    base_right = faces["base_right"]
    latch = faces["latch"]
    lid_height = faces["lid_height"]
    base_height = faces["base_height"]
    face_width = faces["face_width"]

    canvas = Image.new("RGBA", (top.width + face_width * 2, top.height + base_height + lid_height + face_width), (0, 0, 0, 0))

    top_x = face_width
    top_y = 0

    base_y = top.height + lid_height - 2
    lid_y = top.height - 2

    canvas.alpha_composite(top, (top_x, top_y))
    canvas.alpha_composite(base_left, (top_x, base_y))
    canvas.alpha_composite(base_right, (top_x + face_width, base_y))
    canvas.alpha_composite(lid_left, (top_x, lid_y))
    canvas.alpha_composite(lid_right, (top_x + face_width, lid_y))

    latch_x = top_x + face_width + face_width // 2 - latch.width // 2
    latch_y = lid_y + lid_height // 2
    canvas.alpha_composite(latch, (latch_x, latch_y))

    bbox = canvas.getbbox()
    return canvas.crop(bbox) if bbox else canvas


def main() -> None:
    args = parse_args()
    atlas = Image.open(resolve_atlas(args.atlas)).convert("RGBA")
    rendered = render_chest(atlas, args.scale)

    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    output_path = output_dir / f"{args.name}.png"
    rendered.save(output_path)
    print(output_path)


if __name__ == "__main__":
    main()
