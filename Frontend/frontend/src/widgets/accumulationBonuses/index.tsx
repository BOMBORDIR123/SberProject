import BalanceIcon from "@/shared/icons/Balance";
import styles from "./styles.module.scss";
import { message, Select } from "antd";
import { useSelector } from "react-redux";
import { useBuyPromoCode } from "./api/useBuyPromoCode";
import { FC } from "react";

const bonusesItems = [
  {
    id: 1,
    title: "Центр Инвест",
    category: "Банки",
    percent: "-50 %",
    originalPercent: 50,
    price: "1",
    image: "invest",
    desc: "На общественный транспорт",
  },
  {
    id: 2,
    title: "Купер",
    category: "Супермаркеты",
    percent: "-7 %",
    originalPercent: 7,
    price: "1",
    image: "kuper",
  },
  {
    id: 3,
    title: "Додо пицца",
    category: "Фастфуд",
    percent: "-10 %",
    originalPercent: 10,
    price: "135",
    image: "dodo",
  },
  {
    id: 4,
    title: "Мегамаркет",
    category: "Маркетплейсы",
    percent: "-30 %",
    originalPercent: 30,
    price: "128",
    image: "megamarket",
  },
  {
    id: 5,
    title: "Озон",
    category: "Маркетплейсы",
    percent: "-18 %",
    originalPercent: 18,
    price: "188",
    image: "ozon",
  },
];

interface AccumulationBonusesProps {
  refetch: () => void;
}

const AccumulationBonuses: FC<AccumulationBonusesProps> = ({ refetch }) => {
  const [messageApi, contextHolder] = message.useMessage();
  const { currentUser } = useSelector((state: any) => state.currentUser);

  const {
    mutate: buyPromoCode,
    isPending,
    isSuccess,
    data, // Добавьте data
  } = useBuyPromoCode(messageApi);

  const copyPromoCode = (code: string) => {
    navigator.clipboard.writeText(code);
    messageApi.open({
      type: "success",
      content: "Промокод успешно скопирован!",
    });
  };

  return (
    <>
      {contextHolder}
      <div className={styles.container}>
        <h1 className={styles.title}>Потратить бонусы</h1>
        <div className={styles.items}>
          {bonusesItems.map((item) => (
            <div className={styles.item} key={item.id}>
              <div className={styles.info}>
                <img src={`/${item.image}.png`} alt="item" />
                <div className={styles.money}>
                  <div className={styles.count}>
                    <p>{item.price}</p>
                    <BalanceIcon />
                  </div>
                  <p className={styles.bonus}>эко-бонус/а</p>
                </div>
              </div>
              <div className={styles.footer}>
                <p className={styles.category}>{item.category}</p>
                <div className={styles.name}>
                  <p>{item.title}</p>
                  <p>{item.percent}</p>
                </div>
                <p className={styles.desc}>{item?.desc}</p>
                <button
                  className={
                    data?.code && data?.companyName === item.title
                      ? styles.getButtonActive
                      : styles.getButton
                  }
                  onClick={() => {
                    data?.code && data?.companyName === item.title
                      ? copyPromoCode(data?.code)
                      : buyPromoCode({
                          companyName: item.title,
                          discountPercentage: Number(item.originalPercent),
                          price: Number(item.price),
                        });
                    refetch();
                  }}
                  disabled={isPending}
                >
                  {data?.code && data?.companyName === item.title ? (
                    <div className={styles.copy}>
                      <p>{data.code}</p>
                      <img src="./copy.png" alt="copy" />
                    </div>
                  ) : (
                    "Получить скидку"
                  )}
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default AccumulationBonuses;

// import BalanceIcon from "@/shared/icons/Balance";
// import styles from "./styles.module.scss";

// const bonusesItems = [
//   {
//     id: 1,
//     title: "Купер",
//     category: "Супермаркеты",
//     percent: "-7 %",
//     price: "103",
//     image: "kuper",
//   },
//   {
//     id: 2,
//     title: "Додо пицца",
//     category: "Фастфуд",
//     percent: "-10 %",
//     price: "135",
//     image: "dodo",
//   },
//   {
//     id: 3,
//     title: "Мегамаркет",
//     category: "Маркетплейсы",
//     percent: "-30 %",
//     price: "128",
//     image: "megamarket",
//   },
//   {
//     id: 4,
//     title: "Озон",
//     category: "Маркетплейсы",
//     percent: "-18 %",
//     price: "188",
//     image: "ozon",
//   },
// ];

// const AccumulationBonuses = () => {
//   return (
//     <div className={styles.container}>
//       <h1 className={styles.title}>Потратить бонусы</h1>
//       <div className={styles.items}>
//         {bonusesItems.map((item) => (
//           <div className={styles.item}>
//             <div className={styles.info}>
//               <img src={`/${item.image}.png`} alt="item" />
//               <div className={styles.money}>
//                 <div className={styles.count}>
//                   <p>{123}</p>
//                   <BalanceIcon />
//                 </div>
//                 <p className={styles.bonus}>эко-бонуса</p>
//               </div>
//             </div>
//             <div className={styles.footer}>
//               <p className={styles.category}>{item.category}</p>
//               <div className={styles.name}>
//                 <p>{item.title}</p>
//                 <p>{item.percent}</p>
//               </div>
//               <button className={styles.getButton}>Получить скидку</button>
//             </div>
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };

// export default AccumulationBonuses;
