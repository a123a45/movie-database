-- Update movie poster URLs to use local files instead of pexels.com placeholders
-- Movie 1 (星际迷航) - the only jpg poster
UPDATE m_movie SET poster_url = '/uploads/posters/movie_1.jpg' WHERE id = 1;
-- Movies 2-20 all use png posters
UPDATE m_movie SET poster_url = '/uploads/posters/movie_2.png' WHERE id = 2;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_3.png' WHERE id = 3;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_4.png' WHERE id = 4;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_5.png' WHERE id = 5;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_6.png' WHERE id = 6;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_7.png' WHERE id = 7;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_8.png' WHERE id = 8;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_9.png' WHERE id = 9;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_10.png' WHERE id = 10;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_11.png' WHERE id = 11;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_12.png' WHERE id = 12;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_13.png' WHERE id = 13;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_14.png' WHERE id = 14;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_15.png' WHERE id = 15;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_16.png' WHERE id = 16;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_17.png' WHERE id = 17;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_18.png' WHERE id = 18;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_19.png' WHERE id = 19;
UPDATE m_movie SET poster_url = '/uploads/posters/movie_20.png' WHERE id = 20;
