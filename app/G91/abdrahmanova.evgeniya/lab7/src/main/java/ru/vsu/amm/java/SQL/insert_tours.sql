insert into tour(
                 id_tour,
                 id_guide,
                 Title,
                 Description,
                 Duration,
                 Price,
                 Max_participants,
                 Start_location,
                 Language_tour)
values
    (123, 2, 'Tour1', 'Описание Тур1', 2, 15000, 2, 'Пекин', 'CHINESE'),
    (456, 2, 'Tour2', 'Описание Тур2', 2, 13000, 3, 'Москва', 'RUSSIAN'),
    (789, 2, 'Tour3', 'Описание Тур3', 2, 11000, 4, 'Минск', 'BELORUSSIAN')