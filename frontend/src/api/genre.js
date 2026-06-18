import request from './request'

export function getGenreList() {
  return request.get('/genre/list')
}

export function getGenreDetail(id) {
  return request.get(`/genre/${id}`)
}
