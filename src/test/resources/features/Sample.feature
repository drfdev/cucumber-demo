#language: ru
@all
Функционал: Запуск кукумбер-тестов

  @objectUtil
  Сценарий: Проверка создания покемона
    * Создать покемона с перечисленными параметрами
      | id                    | 1                   |
      | name                  | Мой Пикачу          |
      | pokemonClass          | PIKACHU             |
      | pokemonKind           | FIRE                |

      | skills[1].name        | Thunderbolt         |
      | skills[1].description | Убивает все молнией |
      | skills[1].effect      | ATTACK              |
      | skills[1].power       | 100                 |

      | skills[2].name        | Fireblast           |
      | skills[2].description | Убивает все огнем   |
      | skills[2].effect      | ATTACK              |
      | skills[2].power       | 80                  |
      | skills[2].volume      | 0.2                 |

      | bonuses[1].name       | Luck                |
      | bonuses[1].effect     | HEALTH              |
      | bonuses[1].value      | 10                  |

      | defences[1].name      | Basic Defence       |
      | defences[1].defence   | 10                  |
      | defences[1].percent   | 1.2                 |
    * Проверить что покемон на шаге 1 создан
