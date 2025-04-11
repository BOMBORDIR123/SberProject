import { useQuery } from "@tanstack/react-query";
import api from "@/shared/api";

export const useUserMe = () => {
  return useQuery({
    queryKey: ["userMe"],
    queryFn: async () => {
      const { data } = await api.get("/api/user/me", {
        headers: {
          "Content-Type": "application/json",
        },
      });

      return data;
    },
  });
};
