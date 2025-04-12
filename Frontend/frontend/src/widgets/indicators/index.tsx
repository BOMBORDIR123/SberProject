import BalanceIcon from "@/shared/icons/Balance";
import styles from "./styles.module.scss";
import arrowRight from "@/assets/arrowRight.png";
import traceImage from "@/assets/trace.png";
import { Select } from "antd";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

const Indicators = () => {
  const { currentUser } = useSelector((state: any) => state.currentUser);

  return (
    <>
      <div className={styles.container}>
        <div className={styles.balance}>
          <p className={styles.balance__title}>Баланс</p>
          <div className={styles.balance__count}>
            <p>{currentUser?.bonus}</p>
            <BalanceIcon />
          </div>
          <p className={styles.balance__bonus}>эко-бонуса</p>
          <div className={styles.history}>
            <Link to="/history">История эко-бонусов</Link>{" "}
            <img src={arrowRight} alt="arrowRight" />
          </div>
        </div>
        <div className={styles.trace}>
          <p className={styles.balance__title}>Углеродный след</p>
          <div className={styles.trace__count}>
            <p>
              {currentUser?.carbonFootprint ? currentUser?.carbonFootprint : 0}
            </p>
            <img src={traceImage} className={styles.traceImage} alt="trace" />
          </div>
          <Select
            defaultValue="all"
            bordered={false}
            style={{ width: 130 }}
            //   onChange={handleChange}
            options={[
              { value: "all", label: "За все время" },
              { value: "day", label: "За день" },
              { value: "week", label: "За неделю" },
              { value: "month", label: "За месяц" },
              { value: "year", label: "За год" },
            ]}
            className={styles.select}
          />
        </div>
      </div>
    </>
  );
};

export default Indicators;
