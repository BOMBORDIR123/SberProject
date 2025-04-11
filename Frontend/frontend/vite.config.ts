import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tsconfigPaths from "vite-plugin-tsconfig-paths";

interface ProxyConfig {
  target: string;
  changeOrigin: boolean;
  secure: boolean;
}

const proxyConfig: ProxyConfig = {
  target: "http://localhost:8080",
  changeOrigin: true,
  secure: false,
};

const routes: string[] = [
  "/api/auth/registration",
  "/api/auth/login",
  "/api/user/me",
  "/api/receipt/get-all-receipt",
  "/api/promoCode/buy",
];

type ServerProxy = Record<string, ProxyConfig>;

const serverProxy: ServerProxy = routes.reduce((acc, route) => {
  acc[route] = proxyConfig;
  return acc;
}, {} as ServerProxy);

export default defineConfig({
  plugins: [react(), tsconfigPaths()],
  server: {
    proxy: serverProxy,
  },
  build: {
    chunkSizeWarningLimit: Infinity,
  },
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler",
      },
    },
  },
});
