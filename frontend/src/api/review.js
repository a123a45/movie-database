import request from './request'

export function getReviewPage(params) {
  return request.get('/review/page', { params })
}

export function getMyReviews(params) {
  return request.get('/review/my', { params })
}

export function submitReview(data) {
  return request.post('/review', data)
}

export function deleteReview(id) {
  return request.delete(`/review/${id}`)
}
