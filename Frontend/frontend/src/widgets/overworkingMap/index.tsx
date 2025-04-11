import styles from "./styles.module.scss";
import { Map, Placemark, YMaps } from "@pbe/react-yandex-maps";

const OverworkingMap = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Пункты приема и переработки </h1>
      <YMaps>
        <Map
          width="100%"
          height={400}
          defaultState={{ center: [47.22211, 39.718808], zoom: 12 }}
        >
          <Placemark geometry={[47.22211, 39.718808]} />
          <Placemark geometry={[47.23911, 39.718808]} />
          <Placemark geometry={[47.22911, 39.729808]} />
        </Map>
      </YMaps>
    </div>
  );
};

export default OverworkingMap;
