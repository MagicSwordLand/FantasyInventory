# Fantasy Inventory
A totally seperated virtual inventory from the vanilla one.  
Specifically for wearing rpg armors, artifacts and accessories.  
Type `/finv` in game to open up the gui.

## Example configuration

```yml
title: "&a 個人飾品裝備欄"
AllUnique: true
UniqueTypes:
  - ARTIFACT
  - NECKLACE
  - RING
  - AMULET
  - HAND
  - BODYGUARD
  - DOLL  
slots:
  0:
    types:
      - NONE
    icon:
      material: DIAMOND_HOE
      display: "§7§m━━━━━━━━&e ⚇ &b個人資訊&e ⚇ §7§m━━━━━━━━"
      model: 10
      lores:
        - "§x§8§F§E§0§8§C玩家名稱: &f%player_name%"
        - "§x§8§F§E§0§8§C職業等級: &fLv.%class_level%"
        - "§x§8§F§E§0§8§C職業經驗: &f%class_exp_current%/%class_exp_max%"
        - "§x§8§F§E§0§8§C傭兵階級: &r%luckperms_suffix%"
        - "§x§8§F§E§0§8§C傭兵聲望: &f%variableholder_var_傭兵聲望%"
        - "§7§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        - "§x§8§F§E§0§8§C敏捷: &f%class_att_agility% §x§8§F§E§0§8§C體力: &f%class_att_strength% §x§8§F§E§0§8§C智慧: &f%class_att_wisdom%" 
        - "§7§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        - "§x§8§F§E§0§8§C血量: &f%player_health_rounded%/%player_max_health_rounded% §x§8§F§E§0§8§C魔力: &fnone"
        - "§x§8§F§E§0§8§C回血: &fnone §x§8§F§E§0§8§C回魔: &fnone"
        - "§x§8§F§E§0§8§C防禦: &f%mmoitems_stat_Defense% §x§8§F§E§0§8§C傷害: &f%mmoitems_stat_Attack_Damage%"
        - "§x§8§F§E§0§8§C暴率: &f%mmoitems_stat_Critical_Strike_Chance%% §x§8§F§E§0§8§C暴傷: &f%mmoitems_stat_Critical_Strike_Power%"
        - "§x§8§F§E§0§8§C閃避: &f%mmoitems_stat_dodge_rating%% §x§8§F§E§0§8§C速度: &f%mmoitems_stat_Movement_Speed%"
        - "§x§8§F§E§0§8§C近戰傷害加成: &f%mmoitems_stat_physical_damage%"
        - "§x§8§F§E§0§8§C魔法傷害加成: &f%mmoitems_stat_magic_damage%"
        - "§7§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

  
  4:
    types:
      - FHEADWEAR   
      - FASHIONHAT
    icon:
      material: BROWN_STAINED_GLASS_PANE
      display: "\u0026a外觀欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 頭飾"   
  13:
    types:
      - FCHEST    
    icon:
      material: BROWN_STAINED_GLASS_PANE
      display: "\u0026a外觀欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 胸部"    
  22:
    types:
      - FLEG    
    icon:
      material: BROWN_STAINED_GLASS_PANE
      display: "\u0026a外觀欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 腿部" 
  31:
    types:
      - FSHOE    
    icon:
      material: BROWN_STAINED_GLASS_PANE
      display: "\u0026a外觀欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 鞋子"         
  24:
    types:
      - NECKLACE    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 項鏈"  
          
  20:
    types:
      - RING    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 戒指"   
        
  11:
    types:
      - RING    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 戒指"  
        
  28:
    types:
      - DOLL    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 布偶"           
       
  29:
    types:
      - EARRING    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 耳飾"     
   
  33:
    types:
      - BODYGUARD      
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 護身物"
        
  34:
    types:
      - DOLL    
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 布偶"           
        
  39:
    types:
      - HAND   
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 手飾"     
        - ""        
        - "\u0026c*同物品只能裝備一個"         
  40:
    types:
      - ARTIFACT   
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 神器"      
  41:
    types:
      - HAND   
    icon:
      material: GRAY_STAINED_GLASS_PANE
      display: "\u0026a飾品欄位"
      model: 0
      lores:
        - ""
        - "\u00267可接受物品: 手飾"     
        - ""        
        - "\u0026c*同物品只能裝備一個"
```
