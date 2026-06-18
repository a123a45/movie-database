import request from './request'

export function getNewsPage(params) {
  return request.get('/news/page', { params })
}

export function getNewsDetail(id) {
  return request.get(`/news/${id}`)
}
