package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.common.BusinessException;
import com.movie.database.dto.ReviewDto;
import com.movie.database.entity.Movie;
import com.movie.database.entity.Review;
import com.movie.database.entity.User;
import com.movie.database.mapper.MovieMapper;
import com.movie.database.mapper.ReviewMapper;
import com.movie.database.mapper.UserMapper;
import com.movie.database.vo.ReviewVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final MovieMapper movieMapper;
    private final UserMapper userMapper;

    @Transactional
    public void submit(Long userId, ReviewDto dto) {
        Movie movie = movieMapper.selectById(dto.getMovieId());
        if (movie == null) throw new BusinessException("电影不存在");
        Review existing = reviewMapper.selectOne(
                new LambdaQueryWrapper<Review>()
                        .eq(Review::getUserId, userId)
                        .eq(Review::getMovieId, dto.getMovieId())
        );
        if (existing != null) {
            existing.setRating(dto.getRating());
            existing.setTitle(dto.getTitle());
            existing.setContent(dto.getContent());
            reviewMapper.updateById(existing);
        } else {
            Review review = new Review();
            review.setUserId(userId);
            review.setMovieId(dto.getMovieId());
            review.setRating(dto.getRating());
            review.setTitle(dto.getTitle());
            review.setContent(dto.getContent());
            review.setStatus(1);
            reviewMapper.insert(review);
        }
        recalcMovieRating(dto.getMovieId());
    }

    public void delete(Long userId, Long reviewId) {
        Review review = reviewMapper.selectById(reviewId);
        if (review == null) throw new BusinessException("评论不存在");
        if (!review.getUserId().equals(userId)) throw new BusinessException("无权删除他人评论");
        reviewMapper.deleteById(reviewId);
        recalcMovieRating(review.getMovieId());
    }

    private void recalcMovieRating(Long movieId) {
        List<Review> reviews = reviewMapper.selectList(
                new LambdaQueryWrapper<Review>().eq(Review::getMovieId, movieId).eq(Review::getStatus, 1)
        );
        double avg = reviews.stream().mapToDouble(r -> r.getRating().doubleValue()).average().orElse(0);
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setRating(BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP));
        movie.setRatingCount(reviews.size());
        movieMapper.updateById(movie);
    }

    public Page<ReviewVo> page(Long movieId, int page, int size) {
        LambdaQueryWrapper<Review> qw = new LambdaQueryWrapper<Review>()
                .eq(Review::getMovieId, movieId).eq(Review::getStatus, 1)
                .orderByDesc(Review::getCreateTime);
        Page<Review> p = reviewMapper.selectPage(new Page<>(page, size), qw);
        List<ReviewVo> records = p.getRecords().stream().map(r -> {
            User u = userMapper.selectById(r.getUserId());
            return ReviewVo.builder()
                    .id(r.getId()).userId(r.getUserId())
                    .username(u != null ? u.getNickname() : "").userAvatar(u != null ? u.getAvatar() : "")
                    .rating(r.getRating()).title(r.getTitle()).content(r.getContent())
                    .likeCount(r.getLikeCount()).createTime(r.getCreateTime()).build();
        }).collect(Collectors.toList());
        Page<ReviewVo> result = new Page<>();
        result.setCurrent(p.getCurrent()).setSize(p.getSize())
                .setTotal(p.getTotal()).setPages(p.getPages()).setRecords(records);
        return result;
    }

    public Page<ReviewVo> userReviews(Long userId, int page, int size) {
        LambdaQueryWrapper<Review> qw = new LambdaQueryWrapper<Review>()
                .eq(Review::getUserId, userId)
                .orderByDesc(Review::getCreateTime);
        Page<Review> p = reviewMapper.selectPage(new Page<>(page, size), qw);
        List<ReviewVo> records = p.getRecords().stream().map(r -> {
            Movie m = movieMapper.selectById(r.getMovieId());
            User u = userMapper.selectById(r.getUserId());
            return ReviewVo.builder()
                    .id(r.getId()).userId(r.getUserId())
                    .username(u != null ? u.getNickname() : "").userAvatar(u != null ? u.getAvatar() : "")
                    .rating(r.getRating()).title(r.getTitle() != null ? r.getTitle() : (m != null ? m.getTitle() : ""))
                    .content(r.getContent())
                    .likeCount(r.getLikeCount()).createTime(r.getCreateTime()).build();
        }).collect(Collectors.toList());
        Page<ReviewVo> result = new Page<>();
        result.setCurrent(p.getCurrent()).setSize(p.getSize())
                .setTotal(p.getTotal()).setPages(p.getPages()).setRecords(records);
        return result;
    }
}
