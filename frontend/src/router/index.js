import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // Auth (no layout)
  { path: '/login', name: 'Login', component: () => import('@/views/user/LoginView.vue') },
  { path: '/register', name: 'Register', component: () => import('@/views/user/RegisterView.vue') },

  // Public Layout
  {
    path: '/',
    component: () => import('@/layouts/PublicLayout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('@/views/public/HomeView.vue') },
      { path: 'movies', name: 'MovieList', component: () => import('@/views/public/MovieListView.vue') },
      { path: 'movie/:id', name: 'MovieDetail', component: () => import('@/views/public/MovieDetailView.vue') },
      { path: 'movies/genre/:genreId', name: 'MovieGenre', component: () => import('@/views/public/MovieGenreView.vue') },
      { path: 'movies/country/:countryId', name: 'MovieCountry', component: () => import('@/views/public/MovieGenreView.vue') },
      { path: 'movies/year/:year', name: 'MovieYear', component: () => import('@/views/public/MovieGenreView.vue') },
      { path: 'movies/top250', name: 'Top250', component: () => import('@/views/public/TopListView.vue') },
      { path: 'movies/new', name: 'NewMovies', component: () => import('@/views/public/NewMoviesView.vue') },
      { path: 'search', name: 'SearchResult', component: () => import('@/views/public/SearchResultView.vue') },
      { path: 'person/:id', name: 'PersonDetail', component: () => import('@/views/public/PersonDetailView.vue') },
      { path: 'news', name: 'NewsList', component: () => import('@/views/public/NewsListView.vue') },
      { path: 'news/:id', name: 'NewsDetail', component: () => import('@/views/public/NewsDetailView.vue') },
      { path: 'about', name: 'About', component: () => import('@/views/public/AboutView.vue') },
      // User pages (under public layout)
      { path: 'user/profile', name: 'UserProfile', component: () => import('@/views/user/UserProfileView.vue'), meta: { requiresAuth: true } },
      { path: 'user/favorites', name: 'UserFavorites', component: () => import('@/views/user/UserFavoritesView.vue'), meta: { requiresAuth: true } },
      { path: 'user/watchlist', name: 'UserWatchlist', component: () => import('@/views/user/UserWatchlistView.vue'), meta: { requiresAuth: true } },
      { path: 'user/reviews', name: 'UserReviews', component: () => import('@/views/user/UserReviewsView.vue'), meta: { requiresAuth: true } },
    ],
  },

  // Admin Layout
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      { path: '', name: 'AdminDashboard', component: () => import('@/views/admin/AdminDashboardView.vue') },
      { path: 'movies', name: 'AdminMovies', component: () => import('@/views/admin/AdminMovieView.vue') },
      { path: 'movies/edit/:id?', name: 'AdminMovieEdit', component: () => import('@/views/admin/AdminMovieEditView.vue') },
      { path: 'persons', name: 'AdminPersons', component: () => import('@/views/admin/AdminPersonView.vue') },
      { path: 'genres', name: 'AdminGenres', component: () => import('@/views/admin/AdminGenreView.vue') },
      { path: 'users', name: 'AdminUsers', component: () => import('@/views/admin/AdminUserView.vue') },
      { path: 'news', name: 'AdminNews', component: () => import('@/views/admin/AdminNewsView.vue') },
      { path: 'news/edit/:id?', name: 'AdminNewsEdit', component: () => import('@/views/admin/AdminNewsEditView.vue') },
    ],
  },

  // 404
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('@/views/public/NotFoundView.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 }),
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')

  if (to.meta.requiresAuth && !token) {
    next('/login')
    return
  }

  if (to.meta.requiresAdmin && userInfo?.role !== 'ADMIN') {
    next('/')
    return
  }

  next()
})

export default router
