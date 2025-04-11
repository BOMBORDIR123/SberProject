import { useQuery } from "@tanstack/react-query";
import api from "@/shared/api";

export const useGetReceipts = (active: boolean) => {
  return useQuery({
    queryKey: ["useGetReceipts", active],
    queryFn: async () => {
      if (active) {
        const { data } = await api.get("/api/receipt/get-all-receipt", {
          headers: {
            "Content-Type": "application/json",
          },
        });

        return data;
      } else {
        return null;
      }
    },
    enabled: active,
  });
};
