-- ============================================
-- CineBase 电影资料库 — 数据库模式
-- ============================================

-- 用户表
CREATE TABLE IF NOT EXISTS m_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 电影类型表
CREATE TABLE IF NOT EXISTS m_genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    name_en VARCHAR(50),
    sort INT DEFAULT 0,
    description VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 国家地区表
CREATE TABLE IF NOT EXISTS m_country (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    name_en VARCHAR(50),
    sort INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 影人表（导演/演员）
CREATE TABLE IF NOT EXISTS m_person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    name_en VARCHAR(100),
    avatar VARCHAR(255),
    gender INT DEFAULT 0,
    birth_date DATE,
    nationality VARCHAR(50),
    biography TEXT,
    type VARCHAR(20) NOT NULL DEFAULT 'ACTOR',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 电影表
CREATE TABLE IF NOT EXISTS m_movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    title_en VARCHAR(200),
    year INT NOT NULL,
    duration INT,
    country_id BIGINT,
    language VARCHAR(50) DEFAULT '汉语普通话',
    release_date DATE,
    poster_url VARCHAR(500),
    trailer_url VARCHAR(500),
    synopsis TEXT,
    rating DECIMAL(4,1) DEFAULT 0,
    rating_count INT DEFAULT 0,
    box_office VARCHAR(50),
    budget VARCHAR(50),
    content_type VARCHAR(20) DEFAULT 'MOVIE',
    status INT DEFAULT 1,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_year (year),
    INDEX idx_rating (rating),
    INDEX idx_country (country_id),
    INDEX idx_view_count (view_count),
    INDEX idx_content_type (content_type),
    FULLTEXT INDEX ft_title_synopsis (title, synopsis)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 电影-类型关联表
CREATE TABLE IF NOT EXISTS m_movie_genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    UNIQUE KEY uk_movie_genre (movie_id, genre_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 电影-影人关联表
CREATE TABLE IF NOT EXISTS m_movie_person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    role_type VARCHAR(20) NOT NULL,
    role_name VARCHAR(100),
    sort INT DEFAULT 0,
    UNIQUE KEY uk_movie_person_role (movie_id, person_id, role_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 影评表
CREATE TABLE IF NOT EXISTS m_review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating DECIMAL(4,1) NOT NULL,
    title VARCHAR(200),
    content TEXT,
    like_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_movie (user_id, movie_id),
    INDEX idx_movie_id (movie_id),
    INDEX idx_rating (rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏表
CREATE TABLE IF NOT EXISTS m_favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_movie (user_id, movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 想看表
CREATE TABLE IF NOT EXISTS m_watchlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_movie (user_id, movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 电影资讯表
CREATE TABLE IF NOT EXISTS m_news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(500),
    content TEXT,
    cover_url VARCHAR(500),
    movie_id BIGINT,
    status INT DEFAULT 1,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 首页轮播表
CREATE TABLE IF NOT EXISTS m_carousel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    image_url VARCHAR(500) NOT NULL,
    link_url VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
