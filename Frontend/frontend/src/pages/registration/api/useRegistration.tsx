import { useMutation } from "@tanstack/react-query";
import { MessageInstance } from "antd/es/message/interface";

import api from "@/shared/api";
import { FormValues } from "..";

export const useRegistration = (messageApi: MessageInstance) => {
  return useMutation({
    mutationFn: async (form: FormValues) => {
      const { data } = await api.post("/api/auth/registration", form, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      return data;
    },
    onSuccess: async () => {
      messageApi.open({
        type: "success",
        content: "Вы успешно зарегистрированы! А теперь войдите",
      });
    },
    onError: (error: any) => {
      messageApi.open({
        type: "error",
        content:
          typeof error.response.data.message === "string"
            ? error.response.data.message
            : Object.values(error.response.data.message).join(", "),
      });
    },
  });
};
