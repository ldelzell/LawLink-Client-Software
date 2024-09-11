// vite.config.ts
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [
    react(),
    {
      name: 'global-plugin',
      config: () => ({
        define: {
          global: 'window',
        },
      }),
    },
  ],
  server: {
    host: '0.0.0.0',
  },
});
