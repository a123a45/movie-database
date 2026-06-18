"""
Generate cinematic movie poster images with film-themed visual design.
Each poster has: film strip borders, gradient bg, genre badge, year, title overlay.
"""
from PIL import Image, ImageDraw, ImageFont
import os, textwrap, math

UPLOAD_DIR = r'C:\Users\wucan\IdeaProjects\movie-database\backend\uploads\posters'
os.makedirs(UPLOAD_DIR, exist_ok=True)

# Movie data: (id, title, year, genre, color_theme)
movies = [
    (1, '星际迷航：新纪元', 2025, '科幻', (5, 15, 60), (40, 80, 200)),
    (2, '山河无恙', 2024, '战争/剧情', (120, 25, 15), (220, 80, 50)),
    (3, '暗夜追踪', 2025, '悬疑/动作', (20, 20, 45), (80, 80, 180)),
    (4, '夏日的风', 2024, '爱情/剧情', (20, 100, 140), (120, 200, 230)),
    (5, '重生之末日危途', 2026, '科幻/动作', (80, 50, 20), (200, 150, 60)),
    (6, '团圆', 2025, '剧情/家庭', (120, 60, 20), (240, 150, 80)),
    (7, '机械纪元', 2025, '科幻/动作', (10, 40, 80), (50, 120, 200)),
    (8, '春风十里', 2024, '剧情/青春', (40, 100, 60), (130, 200, 150)),
    (9, '迷雾森林', 2025, '悬疑/恐怖', (25, 35, 25), (60, 80, 55)),
    (10, '功夫小子', 2026, '动作/喜剧', (180, 80, 15), (240, 160, 50)),
    (11, '深海迷踪', 2024, '科幻/冒险', (5, 50, 100), (30, 130, 200)),
    (12, '热血街舞', 2025, '剧情/音乐', (160, 20, 60), (230, 80, 130)),
    (13, '时间旅行者', 2025, '科幻/爱情', (60, 15, 100), (150, 60, 200)),
    (14, '山村老宅', 2024, '恐怖/悬疑', (35, 18, 18), (70, 40, 35)),
    (15, '追光者', 2026, '剧情/爱情', (160, 100, 25), (240, 200, 120)),
    (16, '天空之城重制版', 2025, '动画/奇幻', (30, 80, 160), (130, 190, 240)),
    (17, '谍影行动', 2025, '动作/悬疑', (30, 30, 35), (90, 90, 95)),
    (18, '巴黎之恋', 2024, '爱情/剧情', (140, 30, 70), (220, 110, 150)),
    (19, '昆仑之巅', 2026, '动作/冒险', (60, 80, 100), (160, 180, 200)),
    (20, '疯狂实验室', 2026, '喜剧/科幻', (60, 160, 80), (150, 240, 170)),
]

W, H = 400, 600

# Load fonts
font_paths = [
    'C:/Windows/Fonts/msyh.ttc',
    'C:/Windows/Fonts/simhei.ttf',
    'C:/Windows/Fonts/STZHONGS.TTF',
]
font_title = None
font_small = None
for fp in font_paths:
    if os.path.exists(fp):
        try:
            font_title = ImageFont.truetype(fp, 32)
            font_small = ImageFont.truetype(fp, 18)
            break
        except:
            continue
if font_title is None:
    font_title = font_small = ImageFont.load_default()

for movie_id, title, year, genre, color_dark, color_light in movies:
    img = Image.new('RGB', (W, H), color_dark)
    draw = ImageDraw.Draw(img)

    # --- Vertical gradient from dark to lighter ---
    for y in range(H):
        ratio = y / H
        r = int(color_dark[0] + (color_light[0] - color_dark[0]) * ratio)
        g = int(color_dark[1] + (color_light[1] - color_dark[1]) * ratio)
        b = int(color_dark[2] + (color_light[2] - color_dark[2]) * ratio)
        draw.line([(0, y), (W, y)], fill=(r, g, b))

    # --- Film strip perforations (top & bottom) ---
    for x in range(20, W - 10, 45):
        draw.rectangle([x, 8, x + 20, 22], fill=(0, 0, 0))
        draw.rectangle([x, H - 22, x + 20, H - 8], fill=(0, 0, 0))

    # --- Top accent bar ---
    draw.rectangle([(30, 35), (W - 30, 40)], fill=(229, 9, 20))

    # --- Year badge (top right) ---
    draw.rectangle([(W - 85, 55), (W - 25, 80)], fill=(229, 9, 20))
    tw = draw.textbbox((0, 0), str(year), font=font_small)[2]
    draw.text((W - 55 - tw // 2, 58), str(year), fill=(255, 255, 255), font=font_small)

    # --- Genre badge ---
    g_bbox = draw.textbbox((0, 0), genre, font=font_small)
    gw = g_bbox[2] - g_bbox[0] + 20
    draw.rounded_rectangle([(30, 55), (30 + gw, 80)], radius=8, fill=(255, 255, 255, 40))
    draw.text((40, 58), genre, fill=(255, 255, 255), font=font_small)

    # --- Center star/icon ---
    center_text = '★'
    c_bbox = draw.textbbox((0, 0), center_text, font=ImageFont.truetype(fp, 80) if font_title else font_title)
    cw = c_bbox[2] - c_bbox[0]
    icon_font = ImageFont.truetype(fp, 80) if font_title else font_title
    draw.text(((W - cw) // 2, 200), center_text, fill=(255, 255, 255, 60), font=icon_font)

    # --- Title (centered, larger, with shadow effect) ---
    wrapped = textwrap.wrap(title, width=6)
    title_font_large = ImageFont.truetype(fp, 34) if font_title else font_title
    y_start = 320
    for line in wrapped:
        tb = draw.textbbox((0, 0), line, font=title_font_large)
        tw = tb[2] - tb[0]
        x = (W - tw) // 2
        # Shadow
        draw.text((x + 2, y_start + 2), line, fill=(0, 0, 0, 150), font=title_font_large)
        # Text
        draw.text((x, y_start), line, fill=(255, 255, 255), font=title_font_large)
        y_start += 48

    # --- Bottom decorative line ---
    draw.rectangle([(60, y_start + 20), (W - 60, y_start + 22)], fill=(229, 9, 20, 100))

    # --- "CineBase" watermark ---
    wm = 'CINEBASE'
    wm_bbox = draw.textbbox((0, 0), wm, font=font_small)
    ww = wm_bbox[2] - wm_bbox[0]
    draw.text(((W - ww) // 2, y_start + 40), wm, fill=(255, 255, 255, 100), font=font_small)

    path = os.path.join(UPLOAD_DIR, f'movie_{movie_id}.jpg')
    img.save(path, 'JPEG', quality=90)

print(f'Generated {len(movies)} cinematic posters in {UPLOAD_DIR}')
