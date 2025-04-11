import { useMutation } from "@tanstack/react-query";
import { MessageInstance } from "antd/es/message/interface";

import api from "@/shared/api";

interface PromoCodeFormValues {
  companyName: string;
  discountPercentage: number;
  price: number;
}

export const useBuyPromoCode = (messageApi: MessageInstance) => {
  return useMutation({
    mutationFn: async (form: PromoCodeFormValues) => {
      const { data } = await api.post(
        `/api/promoCode/buy?companyName=${form.companyName}&discountPercentage=${form.discountPercentage}&price=${form.price}`
      );

      return data;
    },
    onSuccess: async () => {
      messageApi.open({
        type: "success",
        content: "Промокод успешно куплен!",
      });
    },
    onError: (error: any) => {
      messageApi.open({
        type: "error",
        content: error.response.data.message,
      });
    },
  });
};
