import { BrowserRouter } from "react-router-dom";

import App from "@/App";
import QueryProvider from "./QueryProvider";
import { Provider } from "react-redux";
import { store } from "@/store";

export const Providers = () => {
  return (
    <Provider store={store}>
      <QueryProvider>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </QueryProvider>
    </Provider>
  );
};
