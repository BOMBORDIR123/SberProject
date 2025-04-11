import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  currentUser: null,
};

const currentUserSlice = createSlice({
  name: "currentUser",
  initialState,
  reducers: {
    setCurrentUser: (state, action) => {
      state.currentUser = action.payload;
    },
  },
});

export const currentUserReducer = currentUserSlice.reducer;
export const currentUserActions = currentUserSlice.actions;
