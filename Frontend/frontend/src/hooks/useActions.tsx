import { bindActionCreators } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";

import { currentUserActions } from "@/store/currentUserSlice";

const allActions = {
  ...currentUserActions,
};

export const useActions = () => {
  const dispatch = useDispatch();

  return bindActionCreators(allActions, dispatch);
};
