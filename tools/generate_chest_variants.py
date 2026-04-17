from pathlib import Path
from zipfile import ZipFile
import colorsys

from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
SOURCE_DIR = ROOT / "tools" / "sources"
SRC_CHEST_DIR = ROOT / "src" / "main" / "resources" / "assets" / "rechests" / "textures" / "entity" / "chest"
SRC_BLOCK_DIR = ROOT / "src" / "main" / "resources" / "assets" / "rechests" / "textures" / "blocks"

MINECRAFT_JAR = Path.home() / ".gradle" / "caches" / "minecraft" / "net" / "minecraft" / "minecraft" / "1.7.10" / "minecraft-1.7.10.jar"
BASE_CHEST = SRC_CHEST_DIR / "mbirchchest.png"
BASE_DOUBLE_CHEST = SRC_CHEST_DIR / "moakchest_double.png"
NORMAL_CHEST = SOURCE_DIR / "normal_chest_1.7.10.png"
NORMAL_DOUBLE_CHEST = SOURCE_DIR / "normal_double_chest_1.7.10.png"
TRAPPED_CHEST = SOURCE_DIR / "trapped_chest_1.7.10.png"
TRAPPED_DOUBLE_CHEST = SOURCE_DIR / "trapped_double_chest_1.7.10.png"
BASE_PLANK = SOURCE_DIR / "birch_planks_1.7.10.png"
OAK_BASE_PLANK = SOURCE_DIR / "oak_planks_1.7.10.png"

VARIANTS = {
    "oak": {
        "plank_member": "assets/minecraft/textures/blocks/planks_oak.png",
        "plank_file": SOURCE_DIR / "oak_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "moakchest.png",
        "double_chest_file": SRC_CHEST_DIR / "moakchest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "moaktrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "moaktrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_oak.png",
    },
    "spruce": {
        "plank_member": "assets/minecraft/textures/blocks/planks_spruce.png",
        "plank_file": SOURCE_DIR / "spruce_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "msprucechest.png",
        "double_chest_file": SRC_CHEST_DIR / "msprucechest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "msprucetrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "msprucetrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_spruce.png",
    },
    "birch": {
        "plank_member": "assets/minecraft/textures/blocks/planks_birch.png",
        "plank_file": SOURCE_DIR / "birch_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "mbirchchest.png",
        "double_chest_file": SRC_CHEST_DIR / "mbirchchest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "mbirchtrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "mbirchtrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_birch.png",
    },
    "jungle": {
        "plank_member": "assets/minecraft/textures/blocks/planks_jungle.png",
        "plank_file": SOURCE_DIR / "jungle_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "mjunglechest.png",
        "double_chest_file": SRC_CHEST_DIR / "mjunglechest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "mjungletrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "mjungletrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_jungle.png",
    },
    "acacia": {
        "plank_member": "assets/minecraft/textures/blocks/planks_acacia.png",
        "plank_file": SOURCE_DIR / "acacia_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "macaciachest.png",
        "double_chest_file": SRC_CHEST_DIR / "macaciachest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "macaciatrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "macaciatrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_acacia.png",
    },
    "dark_oak": {
        "plank_member": "assets/minecraft/textures/blocks/planks_big_oak.png",
        "plank_file": SOURCE_DIR / "dark_oak_planks_1.7.10.png",
        "chest_file": SRC_CHEST_DIR / "mdarkoakchest.png",
        "double_chest_file": SRC_CHEST_DIR / "mdarkoakchest_double.png",
        "trapped_chest_file": SRC_CHEST_DIR / "mdarkoaktrappedchest.png",
        "trapped_double_chest_file": SRC_CHEST_DIR / "mdarkoaktrappedchest_double.png",
        "icon_file": SRC_BLOCK_DIR / "planks_dark_oak.png",
    },
}

BAD_TINT_PIXELS = {
    (14, 0), (27, 0), (28, 0), (41, 0),
    (25, 13), (39, 13),
    (13, 15), (27, 15), (41, 15), (55, 15),
    (13, 17), (27, 17), (41, 17), (55, 17),
    (13, 18), (27, 18), (41, 18), (55, 18),
    (14, 19), (27, 19), (28, 19), (41, 19),
    (25, 32), (39, 32),
    (13, 33), (27, 33), (41, 33), (55, 33),
    (13, 34), (27, 34), (41, 34), (55, 34),
    (13, 36), (27, 36), (41, 36), (55, 36),
    (13, 37), (27, 37), (41, 37), (55, 37),
    (13, 38), (27, 38), (41, 38), (55, 38),
    (13, 40), (27, 40), (41, 40), (55, 40),
    (13, 41), (27, 41), (41, 41), (55, 41),
    (3, 42), (4, 42), (6, 42), (10, 42), (11, 42), (12, 42), (13, 42),
    (17, 42), (18, 42), (20, 42), (24, 42), (25, 42), (26, 42), (27, 42),
    (31, 42), (32, 42), (34, 42), (38, 42), (39, 42), (40, 42), (41, 42),
    (45, 42), (46, 42), (48, 42), (52, 42), (53, 42), (54, 42), (55, 42),
}


def extract_missing_planks():
    with ZipFile(MINECRAFT_JAR) as jar:
        for variant in VARIANTS.values():
            plank_file = variant["plank_file"]
            if not plank_file.exists():
                plank_file.write_bytes(jar.read(variant["plank_member"]))

        if not NORMAL_CHEST.exists():
            NORMAL_CHEST.write_bytes(jar.read("assets/minecraft/textures/entity/chest/normal.png"))
        if not NORMAL_DOUBLE_CHEST.exists():
            NORMAL_DOUBLE_CHEST.write_bytes(jar.read("assets/minecraft/textures/entity/chest/normal_double.png"))
        if not TRAPPED_CHEST.exists():
            TRAPPED_CHEST.write_bytes(jar.read("assets/minecraft/textures/entity/chest/trapped.png"))
        if not TRAPPED_DOUBLE_CHEST.exists():
            TRAPPED_DOUBLE_CHEST.write_bytes(jar.read("assets/minecraft/textures/entity/chest/trapped_double.png"))


def rgb_average(image):
    total_r = total_g = total_b = count = 0
    for r, g, b, a in image.getdata():
        if a == 0:
            continue
        total_r += r
        total_g += g
        total_b += b
        count += 1
    return (
        total_r / float(count),
        total_g / float(count),
        total_b / float(count),
    )


def wood_rgb_average(image, normal_template):
    total_r = total_g = total_b = count = 0
    for y in range(image.height):
        for x in range(image.width):
            if not is_wood(normal_template.getpixel((x, y))):
                continue
            r, g, b, a = image.getpixel((x, y))
            if a == 0:
                continue
            total_r += r
            total_g += g
            total_b += b
            count += 1
    return (
        total_r / float(count),
        total_g / float(count),
        total_b / float(count),
    )


def hsv_average(image):
    total_h = total_s = total_v = count = 0.0
    for r, g, b, a in image.getdata():
        if a == 0:
            continue
        h, s, v = colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)
        total_h += h
        total_s += s
        total_v += v
        count += 1.0
    return (
        total_h / count,
        total_s / count,
        total_v / count,
    )


def is_wood(template_pixel):
    r, g, b, a = template_pixel
    if a == 0:
        return False
    if r < 8 and g < 8 and b < 8:
        return False
    if abs(r - g) < 8 and abs(g - b) < 8:
        return False
    return True


def recolor_from_base(base_chest, normal_chest, base_avg, target_avg, target_hsv):
    out = Image.new("RGBA", base_chest.size, (0, 0, 0, 0))
    scale = (
        target_avg[0] / base_avg[0],
        target_avg[1] / base_avg[1],
        target_avg[2] / base_avg[2],
    )

    for y in range(base_chest.height):
        for x in range(base_chest.width):
            pixel = base_chest.getpixel((x, y))
            if not is_wood(normal_chest.getpixel((x, y))):
                out.putpixel((x, y), pixel)
                continue

            r = min(255, int(pixel[0] * scale[0]))
            g = min(255, int(pixel[1] * scale[1]))
            b = min(255, int(pixel[2] * scale[2]))
            h, s, v = colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)

            # Push the wood hue/saturation toward the real plank color,
            # while keeping the brightness from the chest shading.
            target_h, target_s, _ = target_hsv
            corrected_h = target_h
            corrected_s = min(1.0, target_s * 0.9 + s * 0.1)
            corrected_v = v

            nr, ng, nb = colorsys.hsv_to_rgb(corrected_h, corrected_s, corrected_v)
            out.putpixel((x, y), (int(nr * 255), int(ng * 255), int(nb * 255), pixel[3]))

    return out


def derive_style(normal_template, styled_template):
    sat_ratio = 0.0
    val_ratio = 0.0
    count = 0.0

    for y in range(normal_template.height):
        for x in range(normal_template.width):
            vanilla = normal_template.getpixel((x, y))
            styled = styled_template.getpixel((x, y))
            if not is_wood(vanilla):
                continue

            _, vs, vv = colorsys.rgb_to_hsv(vanilla[0] / 255.0, vanilla[1] / 255.0, vanilla[2] / 255.0)
            _, ss, sv = colorsys.rgb_to_hsv(styled[0] / 255.0, styled[1] / 255.0, styled[2] / 255.0)

            sat_ratio += ss / max(vs, 0.001)
            val_ratio += sv / max(vv, 0.001)
            count += 1.0

    return (
        sat_ratio / count,
        val_ratio / count,
    )


def recolor_from_template(normal_chest, target_hsv, sat_scale, val_scale):
    out = Image.new("RGBA", normal_chest.size, (0, 0, 0, 0))
    target_h, target_s, _ = target_hsv

    for y in range(normal_chest.height):
        for x in range(normal_chest.width):
            r, g, b, a = normal_chest.getpixel((x, y))
            if not is_wood((r, g, b, a)):
                out.putpixel((x, y), (r, g, b, a))
                continue

            _, s, v = colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)
            corrected_s = min(1.0, max(0.0, s * sat_scale))
            corrected_s = min(1.0, target_s * 0.85 + corrected_s * 0.15)
            corrected_v = min(1.0, max(0.0, v * val_scale))
            nr, ng, nb = colorsys.hsv_to_rgb(target_h, corrected_s, corrected_v)
            out.putpixel((x, y), (int(nr * 255), int(ng * 255), int(nb * 255), a))

    return out


def build_tiled_planks(template, plank):
    tiled = Image.new("RGBA", template.size, (0, 0, 0, 0))
    for y in range(0, template.height, plank.height):
        for x in range(0, template.width, plank.width):
            tiled.alpha_composite(plank, (x, y))
    return tiled


def build_from_planks(template, plank):
    tiled = build_tiled_planks(template, plank)
    out = Image.new("RGBA", template.size, (0, 0, 0, 0))

    for y in range(template.height):
        for x in range(template.width):
            tr, tg, tb, ta = template.getpixel((x, y))
            if ta == 0:
                continue
            if tr < 8 and tg < 8 and tb < 8:
                out.putpixel((x, y), (0, 0, 0, ta))
                continue
            if not is_wood((tr, tg, tb, ta)):
                out.putpixel((x, y), (tr, tg, tb, ta))
                continue

            pr, pg, pb, _ = tiled.getpixel((x, y))
            brightness = (tr + tg + tb) / (255.0 * 3.0)
            factor = 0.46 + brightness * 0.92
            out.putpixel(
                (x, y),
                (
                    min(255, int(pr * factor)),
                    min(255, int(pg * factor)),
                    min(255, int(pb * factor)),
                    ta,
                ),
            )

    return out


def transfer_single_style(single_template, styled_single, double_template, base_plank, target_plank):
    out = Image.new("RGBA", double_template.size, (0, 0, 0, 0))

    single_style = []
    for y in range(single_template.height):
        for x in range(single_template.width):
            if not is_wood(single_template.getpixel((x, y))):
                continue
            sr, sg, sb, sa = styled_single.getpixel((x, y))
            br, bg, bb, ba = base_plank.getpixel((x % base_plank.width, y % base_plank.height))
            if sa == 0 or ba == 0:
                continue

            style_r = sr / max(br, 1.0)
            style_g = sg / max(bg, 1.0)
            style_b = sb / max(bb, 1.0)
            single_style.append((style_r, style_g, style_b))

    avg_style = (
        sum(s[0] for s in single_style) / len(single_style),
        sum(s[1] for s in single_style) / len(single_style),
        sum(s[2] for s in single_style) / len(single_style),
    )

    tiled_target = build_tiled_planks(double_template, target_plank)

    for y in range(double_template.height):
        for x in range(double_template.width):
            tr, tg, tb, ta = double_template.getpixel((x, y))
            if ta == 0:
                continue
            if tr < 8 and tg < 8 and tb < 8:
                out.putpixel((x, y), (0, 0, 0, ta))
                continue
            if not is_wood((tr, tg, tb, ta)):
                out.putpixel((x, y), (tr, tg, tb, ta))
                continue

            pr, pg, pb, _ = tiled_target.getpixel((x, y))
            brightness = (tr + tg + tb) / (255.0 * 3.0)
            nr = min(255, int(pr * avg_style[0] * (0.72 + brightness * 0.40)))
            ng = min(255, int(pg * avg_style[1] * (0.72 + brightness * 0.40)))
            nb = min(255, int(pb * avg_style[2] * (0.72 + brightness * 0.40)))
            out.putpixel((x, y), (nr, ng, nb, ta))

    return out


def cleanup_bad_tint_pixels(image, normal_chest):
    fixed = image.copy()
    width, height = fixed.size

    for x, y in BAD_TINT_PIXELS:
        samples = []
        for radius in range(1, 5):
            for ny in range(max(0, y - radius), min(height, y + radius + 1)):
                for nx in range(max(0, x - radius), min(width, x + radius + 1)):
                    if (nx, ny) == (x, y) or (nx, ny) in BAD_TINT_PIXELS:
                        continue
                    if not is_wood(normal_chest.getpixel((nx, ny))):
                        continue
                    dist = abs(nx - x) + abs(ny - y)
                    if dist == 0:
                        continue
                    r, g, b, a = fixed.getpixel((nx, ny))
                    if a == 0:
                        continue
                    samples.append((r, g, b, dist))
            if samples:
                break

        if not samples:
            continue

        total_weight = 0.0
        total_r = total_g = total_b = 0.0
        for r, g, b, dist in samples:
            weight = 1.0 / dist
            total_weight += weight
            total_r += r * weight
            total_g += g * weight
            total_b += b * weight

        fixed.putpixel(
            (x, y),
            (
                int(total_r / total_weight),
                int(total_g / total_weight),
                int(total_b / total_weight),
                image.getpixel((x, y))[3],
            ),
        )

    return fixed


def match_wood_average(image, normal_template, target_avg):
    current_avg = wood_rgb_average(image, normal_template)
    scale = (
        target_avg[0] / max(current_avg[0], 1.0),
        target_avg[1] / max(current_avg[1], 1.0),
        target_avg[2] / max(current_avg[2], 1.0),
    )
    fixed = image.copy()
    for y in range(image.height):
        for x in range(image.width):
            if not is_wood(normal_template.getpixel((x, y))):
                continue
            r, g, b, a = image.getpixel((x, y))
            fixed.putpixel(
                (x, y),
                (
                    min(255, int(r * scale[0])),
                    min(255, int(g * scale[1])),
                    min(255, int(b * scale[2])),
                    a,
                ),
            )
    return fixed


def wood_mask_from_image(image):
    mask = set()
    for y in range(image.height):
        for x in range(image.width):
            r, g, b, a = image.getpixel((x, y))
            if a == 0:
                continue
            if r < 8 and g < 8 and b < 8:
                continue
            if abs(r - g) < 10 and abs(g - b) < 10:
                continue
            mask.add((x, y))
    return mask


def recolor_double_from_oak_base(oak_double, oak_plank, target_plank):
    out = oak_double.copy()

    oak_avg = rgb_average(oak_plank)
    target_avg = rgb_average(target_plank)
    oak_h, oak_s, _ = hsv_average(oak_plank)
    target_h, target_s, _ = hsv_average(target_plank)
    wood_mask = wood_mask_from_image(oak_double)

    avg_scale = (
        target_avg[0] / max(oak_avg[0], 1.0),
        target_avg[1] / max(oak_avg[1], 1.0),
        target_avg[2] / max(oak_avg[2], 1.0),
    )

    for x, y in wood_mask:
        r, g, b, a = oak_double.getpixel((x, y))
        rr = min(255, int(r * avg_scale[0]))
        gg = min(255, int(g * avg_scale[1]))
        bb = min(255, int(b * avg_scale[2]))

        h, s, v = colorsys.rgb_to_hsv(rr / 255.0, gg / 255.0, bb / 255.0)
        # Keep the oak-double shading, just rotate the wood hue/sat to the target plank.
        hue_shift = target_h - oak_h
        nh = (h + hue_shift) % 1.0
        ns = min(1.0, max(0.0, s * 0.25 + target_s * 0.75))
        nr, ng, nb = colorsys.hsv_to_rgb(nh, ns, v)
        out.putpixel((x, y), (int(nr * 255), int(ng * 255), int(nb * 255), a))

    return out


def match_double_to_single(single_image, double_image):
    single_mask = wood_mask_from_image(single_image)
    double_mask = wood_mask_from_image(double_image)

    def avg_for_mask(image, mask):
        total_r = total_g = total_b = 0.0
        count = 0.0
        for x, y in mask:
            r, g, b, a = image.getpixel((x, y))
            if a == 0:
                continue
            total_r += r
            total_g += g
            total_b += b
            count += 1.0
        return (
            total_r / count,
            total_g / count,
            total_b / count,
        )

    target_avg = avg_for_mask(single_image, single_mask)
    current_avg = avg_for_mask(double_image, double_mask)
    scale = (
        target_avg[0] / max(current_avg[0], 1.0),
        target_avg[1] / max(current_avg[1], 1.0),
        target_avg[2] / max(current_avg[2], 1.0),
    )

    out = double_image.copy()
    for x, y in double_mask:
        r, g, b, a = out.getpixel((x, y))
        nr = min(255, int(r * scale[0]))
        ng = min(255, int(g * scale[1]))
        nb = min(255, int(b * scale[2]))
        h, s, v = colorsys.rgb_to_hsv(nr / 255.0, ng / 255.0, nb / 255.0)
        th, ts, _ = colorsys.rgb_to_hsv(
            min(255, int(target_avg[0])) / 255.0,
            min(255, int(target_avg[1])) / 255.0,
            min(255, int(target_avg[2])) / 255.0,
        )
        nh = th
        ns = min(1.0, s * 0.35 + ts * 0.65)
        rr, gg, bb = colorsys.hsv_to_rgb(nh, ns, v)
        out.putpixel((x, y), (int(rr * 255), int(gg * 255), int(bb * 255), a))
    return out


def apply_trapped_tint(custom_image, vanilla_normal, vanilla_trapped):
    out = custom_image.copy()
    for y in range(custom_image.height):
        for x in range(custom_image.width):
            nr, ng, nb, na = vanilla_normal.getpixel((x, y))
            tr, tg, tb, ta = vanilla_trapped.getpixel((x, y))
            if na == 0 or ta == 0:
                continue

            dr = tr - nr
            dg = tg - ng
            db = tb - nb
            if abs(dr) + abs(dg) + abs(db) < 20:
                continue

            cr, cg, cb, ca = out.getpixel((x, y))
            out.putpixel(
                (x, y),
                (
                    max(0, min(255, cr + dr)),
                    max(0, min(255, cg + dg)),
                    max(0, min(255, cb + db)),
                    ca,
                ),
            )
    return out


def main():
    extract_missing_planks()
    SRC_CHEST_DIR.mkdir(parents=True, exist_ok=True)
    SRC_BLOCK_DIR.mkdir(parents=True, exist_ok=True)

    base_chest = Image.open(BASE_CHEST).convert("RGBA")
    base_double_chest = Image.open(BASE_DOUBLE_CHEST).convert("RGBA")
    normal_chest = Image.open(NORMAL_CHEST).convert("RGBA")
    normal_double_chest = Image.open(NORMAL_DOUBLE_CHEST).convert("RGBA")
    trapped_chest = Image.open(TRAPPED_CHEST).convert("RGBA")
    trapped_double_chest = Image.open(TRAPPED_DOUBLE_CHEST).convert("RGBA")
    base_plank = Image.open(BASE_PLANK).convert("RGBA")
    oak_base_plank = Image.open(OAK_BASE_PLANK).convert("RGBA")
    base_avg = rgb_average(base_plank)

    for name, variant in VARIANTS.items():
        plank = Image.open(variant["plank_file"]).convert("RGBA")
        target_avg = rgb_average(plank)
        target_hsv = hsv_average(plank)

        chest = recolor_from_base(base_chest, normal_chest, base_avg, target_avg, target_hsv)
        chest = cleanup_bad_tint_pixels(chest, normal_chest)
        if name == "oak":
            double_chest = base_double_chest.copy()
        else:
            double_chest = recolor_double_from_oak_base(base_double_chest, oak_base_plank, plank)
        double_chest = match_double_to_single(chest, double_chest)
        trapped_single = apply_trapped_tint(chest, normal_chest, trapped_chest)
        trapped_double = apply_trapped_tint(double_chest, normal_double_chest, trapped_double_chest)
        chest.save(variant["chest_file"])
        double_chest.save(variant["double_chest_file"])
        trapped_single.save(variant["trapped_chest_file"])
        trapped_double.save(variant["trapped_double_chest_file"])
        plank.save(variant["icon_file"])
        print("generated", name, variant["chest_file"].name)


if __name__ == "__main__":
    main()
