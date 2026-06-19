import request from './request'

// Movie CRUD
export function addMovie(data) { return request.post('/admin/movie', data) }
export function updateMovie(data, config) { return request.put('/admin/movie', data, config) }
export function deleteMovie(id) { return request.delete(`/admin/movie/${id}`) }

// Person CRUD
export function addPerson(data) { return request.post('/admin/person', data) }
export function updatePerson(data) { return request.put('/admin/person', data) }
export function deletePerson(id) { return request.delete(`/admin/person/${id}`) }

// Genre CRUD
export function addGenre(data) { return request.post('/admin/genre', data) }
export function updateGenre(data) { return request.put('/admin/genre', data) }
export function deleteGenre(id) { return request.delete(`/admin/genre/${id}`) }

// User management
export function getUserPage(params) { return request.get('/admin/user/page', { params }) }
export function updateUserStatus(id) { return request.put('/admin/user/status', null, { params: { id } }) }

// Stats
export function getAdminStats() { return request.get('/admin/stats') }

// News CRUD
export function addNews(data) { return request.post('/admin/news', data) }
export function updateNews(data) { return request.put('/admin/news', data) }
export function deleteNews(id) { return request.delete(`/admin/news/${id}`) }

// File upload
// Use FormData — Axios auto-sets Content-Type with correct boundary
export function uploadFile(data) { return request.post('/file/upload', data) }

// Poster upload (auto-crop to 600×900)
export function uploadPoster(data) { return request.post('/file/upload/poster', data) }
