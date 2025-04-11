import { combineReducers } from "@reduxjs/toolkit";
import { currentUserReducer } from "./currentUserSlice";

export const rootReducer = combineReducers({
  currentUser: currentUserReducer,
});
