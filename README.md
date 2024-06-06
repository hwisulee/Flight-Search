# Flight-Search
>JetpackCompose Tutorial - Make Flight Search Project App
>관계형 데이터베이스 읽고 조작, Room을 사용해 데이터베이스 읽기 및 쓰기
>DataStore를 사용한 간단한 데이터 저장 및 Compose를 사용해 다소 복잡한 사용자 인터페이스 빌드

>프로젝트 목표
사용자에게 출발 공항을 묻고 미리 채워진 데이터베이스를 검색하여 해당 공항에서 출발하는 항공편 목록을 표시하고 사용자가 즐겨 찾는 항공편을 저장할 수 있도록 하고 Room 으로 데이터베이스를 업데이트하는 Android 앱

>프로젝트 목표관련 세부 내용
https://developer.android.com/codelabs/basic-android-kotlin-compose-flight-search?hl=ko&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-3%3Fhl%3Dko%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-flight-search#0

<pre>
<code>
개발 언어 : Kotlin</br>
개발 환경 : Windows 10 pro,
            Jdk 1.8
            Android Studio Iguana | 2023.2.1 Patch 1
</code>
</pre>

>추가해야할 것.
1. Favorite Item save and load.
2. DataStore data save and load.

>Application PreView

1. Main Favorite Screen

![image](https://github.com/hwisulee/weatherApp/assets/62528282/db410d0e-7b1c-4d98-afc1-5411fd71d65d)

2. Search Bar Click -> Search Screen (init: Focursing at EditTextField)

![image](https://github.com/hwisulee/weatherApp/assets/62528282/dd0fc272-02ae-4066-a4bf-5a05a7e13d15)

3. Info Button Clicked

![image](https://github.com/hwisulee/weatherApp/assets/62528282/ae78d49a-94a4-492e-89e0-0773f6a85069)

4. User Input -> Search Result

![image](https://github.com/hwisulee/weatherApp/assets/62528282/9529b191-e943-4fed-9dd6-ca2114a24c1b)

5. Favorite Button Clicked(Insert) -> Home Screen -> Favorite Button Clicked(Delete)

![2](https://github.com/hwisulee/Flight-Search/assets/62528282/5c1b25b5-67f0-4b53-9fdd-a0e6e0608f5a)