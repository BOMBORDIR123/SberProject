import AccumulationBonuses from "@/widgets/accumulationBonuses";
import Indicators from "@/widgets/indicators";
import styles from "./styles.module.scss";
import { useGetReceipts } from "./api/useGetReceipts";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useUserMe } from "@/shared/providers/api/useUserMe";
import Header from "@/shared/ui/header";
import OverworkingMap from "@/widgets/overworkingMap";
import HowAccumulate from "@/widgets/howAccumulate";

const HomePage = () => {
  const [isReceipts, setIsReceipts] = useState(false);
  const { currentUser } = useSelector((state: any) => state.currentUser);

  const { data } = useGetReceipts(isReceipts);
  const { data: user, refetch } = useUserMe();

  useEffect(() => {
    if (currentUser && currentUser.receipts.length < 1) {
      setIsReceipts(true);
    }
    refetch();
  }, [currentUser]);

  return (
    <div className={styles.container}>
      <Header />
      <Indicators />
      <AccumulationBonuses refetch={refetch} />
      <HowAccumulate />
      <OverworkingMap />
    </div>
  );
};

export default HomePage;
