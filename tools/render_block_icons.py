from __future__ import annotations

import argparse
from pathlib import Path

from PIL import Image, ImageEnhance


ROOT = Path(__file__).resolve().parent.parent
TEXTURE_ROOT = ROOT / "src" / "main" / "resources" / "assets" / "rechests" / "textures" / "blocks"
OUTPUT_ROOT = ROOT / "tools" / "renders"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Render simple isometric block PNGs from Minecraft block textures."
    )
    parser.add_argument("--name", required=True, help="Output file name without extension.")
    parser.add_argument("--top", required=True, help="Top texture path or file name.")
    parser.add_argument("--side", required=True, help="Side texture path or file name.")
    parser.add_argument("--bottom", help="Bottom texture path or file name. Defaults to --top.")
    parser.add_argument("--right", help="Right face texture path or file name. Defaults to --side.")
    parser.add_argument("--size", type=int, default=16, help="Source texture size in pixels.")
    parser.add_argument("--scale", type=int, default=4, help="Pixel scale multiplier.")
    parser.add_argument("--output-dir", default=str(OUTPUT_ROOT), help="Directory for rendered PNGs.")
    return parser.parse_args()


def resolve_texture(path_str: str) -> Path:
    path = Path(path_str)
    if path.is_file():
        return path

    candidate = TEXTURE_ROOT / path_str
    if candidate.is_file():
        return candidate

    raise FileNotFoundError(f"Texture not found: {path_str}")


def load_texture(path: Path, size: int) -> Image.Image:
    image = Image.open(path).convert("RGBA")
    if image.size != (size, size):
        image = image.resize((size, size), Image.NEAREST)
    return image


def apply_brightness(image: Image.Image, factor: float) -> Image.Image:
    rgb = image.convert("RGB")
    alpha = image.getchannel("A")
    shaded = ImageEnhance.Brightness(rgb).enhance(factor).convert("RGBA")
    shaded.putalpha(alpha)
    return shaded


def scale_texture(image: Image.Image, scale: int) -> Image.Image:
    size = (image.width * scale, image.height * scale)
    return image.resize(size, Image.NEAREST)


def iso_top(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w * 2, h),
        Image.AFFINE,
        (1, -1, 0, 0.5, 0.5, 0),
        resample=Image.NEAREST,
    )


def iso_left(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w, h + h // 2),
        Image.AFFINE,
        (1, 0, 0, -0.5, 0.5, h // 2),
        resample=Image.NEAREST,
    )


def iso_right(image: Image.Image) -> Image.Image:
    w, h = image.size
    return image.transform(
        (w, h + h // 2),
        Image.AFFINE,
        (1, 0, 0, 0.5, 0.5, 0),
        resample=Image.NEAREST,
    )


def render_block(top: Image.Image, left: Image.Image, right: Image.Image, scale: int) -> Image.Image:
    top = scale_texture(top, scale)
    left = scale_texture(left, scale)
    right = scale_texture(right, scale)

    top_face = iso_top(top)
    left_face = iso_left(apply_brightness(left, 0.82))
    right_face = iso_right(apply_brightness(right, 0.68))

    canvas_width = top.width * 2 + top.width
    canvas_height = top.height + top.height + top.height // 2
    canvas = Image.new("RGBA", (canvas_width, canvas_height), (0, 0, 0, 0))

    top_x = (canvas_width - top_face.width) // 2
    top_y = 0
    left_x = top_x
    left_y = top_face.height - 1
    right_x = top_x + top.width
    right_y = top_face.height - 1

    canvas.alpha_composite(top_face, (top_x, top_y))
    canvas.alpha_composite(left_face, (left_x, left_y))
    canvas.alpha_composite(right_face, (right_x, right_y))

    bbox = canvas.getbbox()
    if bbox:
        canvas = canvas.crop(bbox)

    return canvas


def main() -> None:
    args = parse_args()

    top_path = resolve_texture(args.top)
    side_path = resolve_texture(args.side)
    bottom_path = resolve_texture(args.bottom) if args.bottom else top_path
    right_path = resolve_texture(args.right) if args.right else side_path

    top = load_texture(top_path, args.size)
    _bottom = load_texture(bottom_path, args.size)
    left = load_texture(side_path, args.size)
    right = load_texture(right_path, args.size)

    rendered = render_block(top, left, right, args.scale)

    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    output_path = output_dir / f"{args.name}.png"
    rendered.save(output_path)

    print(output_path)


if __name__ == "__main__":
    main()
