import { Link } from "react-router-dom";
import styles from "./styles.module.scss";

const Header = () => {
  return (
    <Link to="/home" className={styles.link}>
      <div className={styles.container}>
        <h1 className={styles.title}>
          Зеленый <br /> Ростов
        </h1>
        <img src="./logo.png" alt="logo" />
      </div>
    </Link>
  );
};

export default Header;
