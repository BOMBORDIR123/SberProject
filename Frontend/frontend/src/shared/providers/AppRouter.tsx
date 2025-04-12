import { Route, Routes, useNavigate } from "react-router-dom";

import { routeConfig, AppRoutes } from "@/shared/lib/routes/RouteConfig";
import { useSelector } from "react-redux";
import { useActions } from "@/hooks/useActions";
import { useUserMe } from "./api/useUserMe";
import { useEffect } from "react";

const AppRouter = () => {
  const { currentUser: user } = useSelector((state: any) => state.currentUser);

  const { setCurrentUser } = useActions();
  const navigate = useNavigate();

  const { data, isSuccess } = useUserMe();

  useEffect(() => {
    if (isSuccess) {
      navigate("/home");
      setCurrentUser(data);
    } else {
      navigate("/");
    }
  }, [user, data]);

  return (
    <Routes>
      {user ? (
        <>
          <Route
            path={routeConfig[AppRoutes.HOME].path}
            element={routeConfig[AppRoutes.HOME].element}
          />
          <Route
            path={routeConfig[AppRoutes.HISTORY].path}
            element={routeConfig[AppRoutes.HISTORY].element}
          />
        </>
      ) : (
        <>
          <Route
            path={routeConfig[AppRoutes.MAIN].path}
            element={routeConfig[AppRoutes.MAIN].element}
          />
          <Route
            path={routeConfig[AppRoutes.REGISTRATION].path}
            element={routeConfig[AppRoutes.REGISTRATION].element}
          />
          <Route
            path={routeConfig[AppRoutes.LOGIN].path}
            element={routeConfig[AppRoutes.LOGIN].element}
          />
        </>
      )}
    </Routes>
  );
};

export default AppRouter;
