import request from './request'

export function getMoviePage(params) {
  return request.get('/movie/page', { params })
}

export function getMovieDetail(id) {
  return request.get(`/movie/${id}`)
}

export function getHotMovies() {
  return request.get('/movie/hot')
}

export function getNewMovies() {
  return request.get('/movie/new')
}

export function getTop250() {
  return request.get('/movie/top250')
}

export function searchMovies(keyword) {
  return request.get('/movie/search', { params: { keyword } })
}

export function getHomeStats() {
  return request.get('/stats/home')
}
