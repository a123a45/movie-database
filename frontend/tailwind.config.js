/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#6366F1',
          hover: '#5558E6',
          light: '#818CF8',
          container: '#1E1B4B',
        },
        surface: {
          DEFAULT: '#1E293B',
          card: '#273548',
          hover: '#334155',
        },
        background: {
          DEFAULT: '#0F172A',
          main: '#020617',
        },
        gold: {
          DEFAULT: '#F59E0B',
          light: '#FBBF24',
        },
        muted: '#94A3B8',
      },
      fontFamily: {
        sans: ['PingFang SC', 'Microsoft YaHei', 'Helvetica Neue', 'Arial', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
