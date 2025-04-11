import React from 'react';
  
import EcoBonus from '@/shared/ui/ecoBonus';
import TakeMuculature from './../../assets/takeMuculature.svg';
import TakeCompost from '../../assets/takeCompost.svg';
import TakePlastic from '../../assets/takePlastic.svg';
import TakaClothes from '../../assets/takeClothes.svg';
import TakeBatary from '../../assets/takeBatary.svg';
import TakeGlass from '../../assets/takeGlass.svg';
import TakeTechnic from '../../assets/takeTechnic.svg';

import styles from './styles.module.scss';

export default function HowAccumulate() {
  return (
    <div>
        <h1 className={styles.title}>Как накопить эко-бонусы</h1>
        <div className={styles.block__scroll_action}>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать мукулатуру<div><img src={TakeMuculature} alt="" /></div></div> <EcoBonus count={3} /></div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать компост<div><img src={TakeCompost} alt="" /></div></div> <EcoBonus count={1} /> </div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать пластик<div><img src={TakePlastic} alt="" /></div></div> <EcoBonus count={5} /></div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать одежду<div><img src={TakaClothes} alt="" /></div></div> <EcoBonus count={2} /></div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать батарейки<div><img src={TakeBatary} alt="" /></div></div> <EcoBonus count={6} /></div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать стекло<div><img src={TakeGlass} alt="" /></div></div> <EcoBonus count={4} /></div>
            <div className={styles.block__about_bonuse}><div className={styles.take__everything}>Сдать технику<div><img src={TakeTechnic} alt="" /></div></div> <EcoBonus count={7} /></div>
        </div>
    </div>
  )
}
