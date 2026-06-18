import request from './request'

export function toggleFavorite(movieId) {
  return request.post(`/favorite/toggle/${movieId}`)
}

export function getFavoritePage(params) {
  return request.get('/favorite/page', { params })
}

export function toggleWatchlist(movieId) {
  return request.post(`/watchlist/toggle/${movieId}`)
}

export function getWatchlistPage(params) {
  return request.get('/watchlist/page', { params })
}
