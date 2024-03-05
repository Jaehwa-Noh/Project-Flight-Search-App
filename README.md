# Project-Flight-Search-App
https://developer.android.com/codelabs/basic-android-kotlin-compose-flight-search#0

Practice makes a master.   
This main branch is the main directory.   

There are 2 branchs for 1 practice, Compose and SwiftUI.   
   
• [Compose branch](https://github.com/Jaehwa-Noh/Project-Flight-Search-App/tree/compose-flight-search-app)   
• [SwiftUI branch]   


---

연습이 장인을 만든다.   
이 메인 브랜치는 메인 폴더입니다.

1개의 연습에 컴포즈와 스위프트유아이 2개의 브랜치가 있습니다.   
   
• [컴포즈 브랜치](https://github.com/Jaehwa-Noh/Project-Flight-Search-App/tree/compose-flight-search-app)   
• [스위프트유아이 브랜치]   


## Flight Search App (비행기 검색 앱)
Search the departure aireport and show the route from departure airport to destination airport.   
(출발 공항을 검색하고 출발 공항과 도착 공항 비행기편을 보여줍니다.)   
And also you can bookmark the favorite routes.   
(그리고, 선호하는 항공편을 책갈피 지정할 수 있습니다.)   

## Specifications (명세서)   
### Requirements (요구 사항)

- [x] • Provide a text field for the user to enter an airport name or International Air Transport Association (IATA) airport identifier.   
(공항 이름 또는 국제 항공 운송 협회 (IATA) 공항 고유 코드를 적을 수 있는 텍스트 필드를 제공합니다.)   

- [x] • Query the database to provide autocomplete suggestions as the user types.   
(사용자 입력을 쿼리로 이용해서 데이터베이스의 자료를 불러와 자동완성 제안을 보여줍니다.)   

- [x] • When the user chooses a suggestion, generate a list of available flights from that airport, including the IATA identifier and airport name to other airports in the database.   
(사용자가 완성된 제안을 눌렀을 때, 해당 공항에서 이용 가능한 비행기를 보여주고, IATA 코드와 도착 공항 이름을 데이터베이스에서 불러와 보여줍니다.)   

- [x] • Let the user save favorite individual routes.   
(사용자가 개별 항로를 선호하는 경로로 저장 할 수 있습니다.)   

- [x] • When no search query is entered, display all the user-selected favorite routes in a list.   
(검색 글자가 없을 때, 사용자가 저장한 선호 경로를 표시합니다.)    

- [x] • Save the search text with Preferences DataStore. When the user reopens the app, the search text, if any, needs to prepopulate the text field with appropriate results from the database.   
(검색 글자를 DataStore에 저장하여 사용자가 다시 앱을 열었을 때, 검색 글자를 넣고, 검색 글자가 존재한다면, 검색 결과를 표시합니다.)   


#### Additional requirements (추가 요구 사항)
- [x] • Search for autocomplete suggestions in the airport table. Keep in mind that the user might already know the airport code, so you need to check their input against the iata_code column, in addition to the name column, when searching for text. Remember that you can use the LIKE keyword to perform text searches.   
(공항 테이블에서 자동완성 제안을 위한 공항을 찾습니다. 사용자는 이미 공항 코드를 알고 있다고 가정합니다. 그래서 당신은 iata_code 컬럼을 확인하면 됩니다. 추가적으로 사용자가 이름으로 검색할 때에는 이름 컬럼에서 찾습니다. 당신은 LIKE 단어를 사용하여 글 찾기를 수행할 수 있다는 것을 기억하세요.)   

- [x] • Show more frequently visited airports in descending order by sorting on the passengers column.   
(이용자가 많은 공항을 승객 칼럼의 수로 내림 차순 정렬하여 보여주세요.)   

- [x] • Assume that every airport has flights to every other airport in the database (except for itself).   
(모든 공항은 다른 공항으로 가는 항공기를 데이터베이스에 저장되어 있다고 가정합니다. (출발 공항 제외))   

- [x] • When no text is in the search box, display a list of favorite flights, showing the departure and destination. As the favorite table only includes columns for the airport codes, you're not expected to show the airport names in this list.   
(검색 상자에 글자가 없을 때에는 출발지, 목적지가 적힌 선호하는 항공편 목록을 보여줍니다. favorite 테이블은 airport codes 칼럼만 가지고 있습니다. 당신은 공항 이름을 선호 목록에 보여줄 필요가 없습니다.)   

- [x] • Perform all database querying with SQL and Room APIs. The whole point is to NOT load your entire database into memory at once, only to retrieve the required data as needed.  
(모든 데이터베이스 query 실행은 SQL과 Room APIs로 합니다. 모든 데이터베이스를 메모리에 한 번에 불러오지 않아야 합니다. 단지, 필요한 데이터만 찾아야 합니다.)   


### Full screen (전체 화면)
|Search (검색)|Select (선택)|Empty (빈)|
|------------|-------------|----------|
|<img width="250" alt="Search (검색)" src="https://github.com/Jaehwa-Noh/Project-Flight-Search-App/assets/48680511/ca31fb60-919f-4979-aff1-4efef9c37cfe">|<img width="250" alt="Select (선택)" src="https://github.com/Jaehwa-Noh/Project-Flight-Search-App/assets/48680511/50704804-c54a-4759-9e48-302846eea671">|<img width="250" alt="Empty (빈)" src="https://github.com/Jaehwa-Noh/Project-Flight-Search-App/assets/48680511/b53060b8-dc7d-4d3a-8220-935918403952">|


### Database (데이터베이스)
#### airport table (공항 테이블)
|Column (항목)|Data type (데이터 형식)|Description (설명)|
|------|---------|-----------|
|id|INTEGER|Unique identifier (primary key) (고유한 아이디 (프라이머리 키))|
|iata_code|VARCHAR|3 letter IATA code (3 자리수 IATA 코드)|
|name|VARCHAR|Full airport name (전체 공항 이름)|
|passengers|INTEGER|Number of passengers per year (연간 이용 승객 수)|

#### favorite table (선호 테이블)
|Column (항목)|Data type (데이터 형식)|Description (설명)|
|------|---------|-----------|
|id|INTEGER|Unique identifier (primary key) (고유한 아이디 (프라이머리 키))|
|departure_code|VARCHAR|IATA code for departure (출발지 IATA 코드)|
|destination_code|VARCHAR|IATA code for destination (도착지 IATA 코드)|

## Resources (재료)
### Database (데이터베이스)
[flight_search.db](https://github.com/google-developer-training/android-basics-kotlin-sql-basics-app/blob/project/flight_search.db)

## Problem Solve (문제 해결)
### Compose (컴포즈)
#### [Using Room database LIKE with wildcard (룸 데이터베이스 LIKE 단어 와일드 카드와 같이 사용)](https://shwoghk14.blogspot.com/2024/02/android-room-database-like-with-wildcard.html)
#### [Row child goes out of the screen (Row 자식이 화면 밖으로 나감)](https://shwoghk14.blogspot.com/2024/03/android-compose-row-child-goes-out-of.html)
#### [Improve time complexity, using HashMap, make big(O) 2N to N+1 (해쉬맵을 사용해서 시간 복잡도 개선, big(O) 2N을 N+1로 변경)](https://shwoghk14.blogspot.com/2024/03/android-improve-time-complexity-using.html)
#### [Return emptyFlow doesn't collected (EmptyFlow 반환값이 수집되지 않음)](https://shwoghk14.blogspot.com/2024/03/android-emptyflow-and-flowofemptylist.html)
