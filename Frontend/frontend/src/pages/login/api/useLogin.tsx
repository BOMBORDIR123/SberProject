import { useMutation } from "@tanstack/react-query";
import { MessageInstance } from "antd/es/message/interface";

import api from "@/shared/api";
import { FormValues } from "..";

export const useLogin = (messageApi: MessageInstance) => {
  return useMutation({
    mutationFn: async (form: FormValues) => {
      const { data } = await api.post("/api/auth/login", form, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      return data;
    },
    onSuccess: async () => {
      messageApi.open({
        type: "success",
        content: "Вы успешно вошли!",
      });
    },
    onError: (error: any) => {
      messageApi.open({
        type: "error",
        content:
          error.response.data.message === "Incorrect data"
            ? "Неправильный номер телефона или пароль"
            : Object.values(error.response.data.message).join(", "),
      });
    },
  });
};
