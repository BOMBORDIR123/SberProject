import Header from "@/shared/ui/header";
import { data } from "./data";
// import { useGetReceipts } from "./api/useGetReceipts";
import styles from "./styles.module.scss";
import { Skeleton } from "antd";

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString("ru-RU", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });
};

const History = () => {
  //   const { data } = useGetReceipts();

  if (!data) {
    <Skeleton />;
  }

  return (
    <div>
      <Header />
      <div className={styles.container}>
        <div className={styles.title}>История эко-бонусов</div>
        <div className={styles.content}>
          {data &&
            data?.map((item) => (
              <div className={styles.item} key={item.id}>
                <div className={styles.item__header}>
                  <div className={styles.item__date}>
                    {formatDate(item.date)}
                  </div>
                  <div className={styles.item__bonus}>{item.bonus}</div>
                  <div className={styles.item__carbon}>
                    {item.carbonFootprint.toFixed(1)}
                  </div>
                </div>
                <div className={styles.item__products}>
                  {item.items.map((product) => (
                    <div className={styles.product} key={product.id}>
                      <div className={styles.product__name}>{product.name}</div>
                      <div className={styles.product__details}>
                        <div className={styles.product__quantity}>
                          Количество: {product.quantity}
                        </div>
                        <div className={styles.product__carbon}>
                          {product.carbonFootprint.toFixed(1)}
                        </div>
                        {product.isEcoFriendly ? (
                          <div className={styles.product__eco}>Эко-товар</div>
                        ) : (
                          <div className={styles.product__notEco}>
                            Не эко-товар
                          </div>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default History;
