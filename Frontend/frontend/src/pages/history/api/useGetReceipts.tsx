import { useQuery } from "@tanstack/react-query";
import api from "@/shared/api";

export const useGetReceipts = () => {
  return useQuery({
    queryKey: ["useGetReceipts"],
    queryFn: async () => {
      const { data } = await api.get("/api/receipt/get-all-receipt", {
        headers: {
          "Content-Type": "application/json",
        },
      });

      return data;
    },
  });
};
