import styles from "./styles.module.scss";

const Header = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>
        Зеленый <br /> Ростов
      </h1>
      <img src="./logo.png" alt="logo" />
    </div>
  );
};

export default Header;
