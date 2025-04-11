import React from 'react';
import Leaveas from '../../../assets/Leaveas.svg';
import styles from './styles.module.scss';
export default function EcoBonus(props: { count: number; }) {
  return (
    <div className={styles.button__bonuses}>
        <div className={styles.center__bonuses}>{props.count}<div className={styles.bonus}>Эко бонуса</div></div>
        <div><img src={Leaveas} alt="" /></div>
    </div>
  )
}
