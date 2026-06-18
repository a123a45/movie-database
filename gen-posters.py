"""Generate 20 placeholder movie poster images with titles using PIL."""
from PIL import Image, ImageDraw, ImageFont
import os
import textwrap

UPLOAD_DIR = r'C:\Users\wucan\IdeaProjects\movie-database\backend\uploads\posters'

# Movie data: (id, title, color)
movies = [
    (1, '星际迷航：新纪元', (10, 20, 60)),
    (2, '山河无恙', (80, 20, 20)),
    (3, '暗夜追踪', (30, 30, 50)),
    (4, '夏日的风', (20, 60, 80)),
    (5, '重生之末日危途', (60, 40, 20)),
    (6, '团圆', (80, 40, 20)),
    (7, '机械纪元', (20, 50, 80)),
    (8, '春风十里', (30, 70, 50)),
    (9, '迷雾森林', (20, 40, 30)),
    (10, '功夫小子', (80, 50, 20)),
    (11, '深海迷踪', (10, 40, 80)),
    (12, '热血街舞', (80, 20, 40)),
    (13, '时间旅行者', (40, 20, 80)),
    (14, '山村老宅', (30, 20, 20)),
    (15, '追光者', (80, 60, 20)),
    (16, '天空之城重制版', (20, 60, 100)),
    (17, '谍影行动', (20, 30, 40)),
    (18, '巴黎之恋', (80, 30, 60)),
    (19, '昆仑之巅', (40, 60, 80)),
    (20, '疯狂实验室', (60, 80, 20)),
]

W, H = 400, 600  # 2:3 poster ratio

for movie_id, title, color in movies:
    img = Image.new('RGB', (W, H), color)
    draw = ImageDraw.Draw(img)

    # Draw gradient overlay at bottom for text readability
    for i in range(150):
        alpha = i / 150
        r = int(color[0] * (1 - alpha))
        g = int(color[1] * (1 - alpha))
        b = int(color[2] * (1 - alpha))
        draw.rectangle([(0, H - 150 + i), (W, H - 150 + i)], fill=(r, g, b))

    # Draw decorative line
    draw.rectangle([(20, H - 170), (100, H - 165)], fill=(229, 9, 20))

    # Try to use a Chinese font, fallback to default
    font = None
    font_paths = [
        'C:/Windows/Fonts/msyh.ttc',
        'C:/Windows/Fonts/simhei.ttf',
        'C:/Windows/Fonts/STZHONGS.TTF',
        'C:/Windows/Fonts/simsun.ttc',
    ]
    for fp in font_paths:
        if os.path.exists(fp):
            try:
                font = ImageFont.truetype(fp, 36)
                break
            except:
                continue

    if font is None:
        font = ImageFont.load_default()

    # Draw title (wrap long titles)
    wrapped = textwrap.wrap(title, width=8)
    y = H - 120
    for line in wrapped:
        bbox = draw.textbbox((0, 0), line, font=font)
        tw = bbox[2] - bbox[0]
        draw.text(((W - tw) / 2, y), line, fill=(255, 255, 255), font=font)
        y += 50

    # Draw movie icon
    icon_font = None
    for fp in font_paths:
        if os.path.exists(fp):
            try:
                icon_font = ImageFont.truetype(fp, 120)
                break
            except:
                continue
    if icon_font:
        draw.text((W / 2, H / 2 - 60), '🎬', fill=(255, 255, 255, 128), font=icon_font, anchor='mm')

    path = os.path.join(UPLOAD_DIR, f'movie_{movie_id}.jpg')
    img.save(path, 'JPEG', quality=85)
    print(f'Generated: movie_{movie_id}.jpg — {title}')

print(f'\nDone! {len(movies)} posters in {UPLOAD_DIR}')
