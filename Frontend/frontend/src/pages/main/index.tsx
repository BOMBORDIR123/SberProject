import styles from "./styles.module.scss";
import ClearPlanetSVG from "../../assets/ClearPlanetSVG.svg";
import EcologyHand from "../../assets/EcologyHand.svg";
import { Link } from "react-router-dom";

const MainPage = () => {
  return (
    <div className={styles.container}>
      <header>
        <p>
          Зелёный
          <br />
          Ростов
        </p>{" "}
        <img src={EcologyHand} alt="Ecology" />
      </header>
      {/* <div className={styles.block}>
        
        <h1 className={styles.title}>
          <span>Сделаем нашу</span> планету{" "}
          <div className={styles.clearing}>чище</div>!
        </h1>
        <div>hello</div>
      </div> */}
      <div className={styles.wrapper}>
        <div>
          <h1 className={styles.subtitle__mobile}>
            Сделаем нашу планету <span>чище!</span>
          </h1>
          <div className={styles.block__logo}>
            <img src={ClearPlanetSVG} alt="logo" />
          </div>
        </div>
        <div className={styles.block__flex}>
          <h1 className={styles.subtitle}>
            Сделаем нашу планету <span>чище!</span>
          </h1>
          <div>
            <p className={styles.text__description}>
              Казалось бы, как может один человек остановить загрязнение Земли?
              А уж тем более то, что он ест и пьет. Но влияние колоссальное! То,
              как питается один единственный человек уже оказывает воздействие
              на окружающую среду. Чтобы показать, как питание одного человека
              влияет на состояние планеты и что можно с этим сделать, мы
              подготовили список вопросов и ответов на них.
            </p>
            <Link to="/registration">
              <button className={styles.button__do_contribution}>
                Сделать вклад
              </button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
