import { lazy, Suspense } from "react";
import { RouteProps } from "react-router-dom";

const LazyMainPage = lazy(() => import("@/pages/main"));
const LazyRegistrationPage = lazy(() => import("@/pages/registration"));
const LazyLoginPage = lazy(() => import("@/pages/login"));
const LazyHomePage = lazy(() => import("@/pages/home"));
const LazyHistoryPage = lazy(() => import("@/pages/history"));

export enum AppRoutes {
  MAIN = "main",
  REGISTRATION = "registration",
  LOGIN = "login",
  HOME = "home",
  HISTORY = "history",
}

export const RoutePath: Record<AppRoutes, string> = {
  [AppRoutes.MAIN]: "/",
  [AppRoutes.REGISTRATION]: "/registration",
  [AppRoutes.LOGIN]: "/login",
  [AppRoutes.HOME]: "/home",
  [AppRoutes.HISTORY]: "/history",
};

export const routeConfig: Record<AppRoutes, RouteProps> = {
  [AppRoutes.MAIN]: {
    path: RoutePath.main,
    element: (
      <Suspense fallback={<></>}>
        <LazyMainPage />
      </Suspense>
    ),
  },
  [AppRoutes.REGISTRATION]: {
    path: RoutePath.registration,
    element: (
      <Suspense fallback={<></>}>
        <LazyRegistrationPage />
      </Suspense>
    ),
  },
  [AppRoutes.LOGIN]: {
    path: RoutePath.login,
    element: (
      <Suspense fallback={<></>}>
        <LazyLoginPage />
      </Suspense>
    ),
  },
  [AppRoutes.HOME]: {
    path: RoutePath.home,
    element: (
      <Suspense fallback={<></>}>
        <LazyHomePage />
      </Suspense>
    ),
  },
  [AppRoutes.HISTORY]: {
    path: RoutePath.history,
    element: (
      <Suspense fallback={<></>}>
        <LazyHistoryPage />
      </Suspense>
    ),
  },
};
