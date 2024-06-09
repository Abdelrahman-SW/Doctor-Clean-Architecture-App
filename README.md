<h1 align="center"> <img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/> 
  Doctor-Clean-Architecture-App
  <img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/>
</h1> 
<h3 align="center" > --- An Android App In Kotlin & Jetpack Compose ---</h3>

<p align="center"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/>   
<img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-icon.svg" alt="kotlin" width="40" height="40"/>  
<img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/> 
</p>

<h2 align="left">About :</h2>

The Doctor App is an Implementation to The Clean Architecture app code, that let the code fit well and be scalable and can 
be easily extendable.
The app is made to apply most of clean arch tips and guidelines 
The main idea of ​​the app is that : it let the doctor search for his patients that are registered with him
and can see all files that share with each other and takes notes and draw graphs and more..
Actually the most important point on the app that i put the focus on it is the way I coded it and how I follow the clean arch guidelines
so let`s talk more about how i acheived the clean arch guidlines on this app code.

<h2 align="left">Tools & Technloges Used in The App : </h2>

-- Jetpack Compose : For Design The Ui in a modern declarative UI way For Android

-- Firebase Storage , Firebase Firestore : For Auth and save patients files and graphs and doctor notes

-- MPAndroidChart Lib To Draw Charts , link of lib 🔗 : https://github.com/PhilJay/MPAndroidChart

-- coroutines , flows , viewModel , ForegroundServices , Notification SDk and more ..


<h2 align="left">How The Clean Arch Is Applied In The Code ? :</h2>

<p align="left">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/3f7f306e-0620-4e1f-b646-cf369decd298" width="420" height="640">
</p>

The code Is Splitted By Features so each Feature in the code has it`s own package this let the code more scalable and can be easily converted to 
multi-module code as each feature is an isolated moudle
then inside each feature the code is packaged by 3 different layers : presentation,domain,data and the di for dependency Injection , so what is the benefit of that
and why this considered as a clean arch code lets talk about this in next section.


<h2>Splitting Android Code into Presentation, Domain, and Data Layers: Benefits and Best Practices</h2>

<p>In Android development, organizing your code into distinct layers—Presentation, Domain, and Data—is a powerful architectural strategy. This separation of concerns improves code maintainability, testability, and scalability. Let's explore each layer and the benefits of this approach.</p>

<h3>1. Presentation Layer</h3>
<p><strong>Description:</strong><br>
The Presentation layer is responsible for displaying data to the user and handling user interactions. It typically includes Activities, Fragments, Views, and ViewModels in MVVM architecture.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Managing UI components and rendering data.</li>
  <li>Handling user inputs and updating the UI accordingly.</li>
  <li>Communicating with the ViewModel to fetch or send data.</li>
</ul>

<h3>2. Domain Layer</h3>
<p><strong>Description:</strong><br>
The Domain layer contains the business logic of the application. It is independent of any other layers, making it easy to test and reuse.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Encapsulating the core business logic.</li>
  <li>Defining use cases or interactors that represent specific business actions.</li>
  <li>Providing a clear API for the Presentation layer.</li>
</ul>

<h3>3. Data Layer</h3>
<p><strong>Description:</strong><br>
The Data layer is responsible for managing data sources. It includes repositories, data sources (remote and local), and data models. This layer abstracts the data operations from the rest of the application.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Handling data operations, such as network requests, database queries, and caching.</li>
  <li>Providing a unified data interface to the Domain layer.</li>
  <li>Managing data models and mappings.</li>
</ul>

<h3>Benefits of Layered Architecture</h3>
<p><strong>Separation of Concerns:</strong></p>
<ul>
  <li>Each layer has a distinct responsibility, making the code easier to understand, maintain, and extend.</li>
</ul>

<p><strong>Testability:</strong></p>
<ul>
  <li>Isolated layers allow for easier unit testing. For example, the Domain layer can be tested without dependencies on the Presentation or Data layers.</li>
</ul>

<p><strong>Reusability:</strong></p>
<ul>
  <li>Business logic encapsulated in the Domain layer can be reused across different parts of the application or even in different applications.</li>
</ul>

<p><strong>Maintainability:</strong></p>
<ul>
  <li>Changes in one layer (e.g., switching from a REST API to GraphQL in the Data layer) have minimal impact on other layers.</li>
</ul>

<p><strong>Scalability:</strong></p>
<ul>
  <li>The clear separation allows for easier scaling of the codebase. Different teams can work on different layers simultaneously without causing conflicts.</li>
</ul>

<br></br>

<h2>Defining Concrete Interfaces in Software Development</h2>

<p align="left">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/3c58ab55-e762-4668-bb57-850a4cb6cc38" width="1080" height="720">
</p>

<p>Concrete interfaces in software development refer to well-defined contracts that specify the methods and behaviors expected from classes that implement them. They provide several key benefits:</p>

<h3>Abstraction</h3>
<p>Interfaces allow you to define what methods a class should implement without specifying how these methods should be implemented. This separates the "what" from the "how."</p>

<h3>Flexibility and Extensibility</h3>
<p>Interfaces enable different classes to implement the same set of methods in various ways, facilitating polymorphism and making it easy to extend and modify code without altering existing implementations.</p>

<h3>Testability</h3>
<p>By coding to an interface, you can easily substitute mock implementations during testing, enhancing the testability of your code.</p>

<h3>Decoupling</h3>
<p>Interfaces promote loose coupling between components, making the system more modular and easier to maintain.</p>

<br></br>

<h2>Benefits of Dependency Injection in Clean Architecture</h2>

<p align="left">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/a96dacd4-9230-4abb-847f-63912bfd94ba" width="460" height="240">
</p>

<h3>I Used In The Code The Dagger-hilt For dependency Injection so let`s talk about benefits of applying the di in the Code</h3>

<p>Dependency Injection (DI) is a design pattern used to implement Inversion of Control (IoC), allowing the creation of dependent objects outside of a class and providing those objects to the class through various means. In the context of Clean Architecture, DI offers several significant benefits:</p>

<h3>Separation of Concerns</h3>
<p>DI helps in separating the creation of dependencies from the business logic, allowing different components of the application to focus on their primary responsibilities without being concerned with how their dependencies are instantiated.</p>

<h3>Easier Testing</h3>
<p>By injecting dependencies, you can easily replace real implementations with mock or stub versions during testing. This makes unit tests more straightforward and faster, as you can isolate the components being tested.</p>

<h3>Improved Maintainability</h3>
<p>When dependencies are injected, it becomes easier to manage and update the dependencies without changing the classes that use them. This reduces the effort needed to maintain and update the codebase.</p>

<h3>Enhanced Flexibility</h3>
<p>DI allows for greater flexibility in choosing which implementations of a dependency to use at runtime. This is particularly useful in Clean Architecture, where different layers (Presentation, Domain, Data) may have different requirements and implementations.</p>

<h3>Reduced Coupling</h3>
<p>By relying on interfaces rather than concrete implementations, DI reduces the coupling between classes. This leads to more modular code, where changes in one part of the system have minimal impact on other parts.</p>

<h3>Promotes SOLID Principles</h3>
<p>DI encourages adherence to SOLID principles, particularly the Dependency Inversion Principle, which states that high-level modules should not depend on low-level modules but rather on abstractions.</p>

<br></br>

<h2>MVVM and Clean Architecture</h2>

<h3>I Followed The MvvM design pattern In This app Code Let`s Talk what is MvvM And how MVVM Aligns with Clean Architecture :</h3>

<p>MVVM (Model-View-ViewModel) is a design pattern that helps separate concerns in an application, promoting a clear separation between the user interface and the business logic. When combined with Clean Architecture principles, MVVM enhances code maintainability, testability, and scalability. Here's a brief overview of how MVVM fits into the Clean Architecture approach:</p>

<h3>1. Model</h3>
<p>The Model represents the data and business logic of the application. In Clean Architecture, the Model is part of the Domain layer, encapsulating the core functionality and rules of the application.</p>

<h3>2. View</h3>
<p>The View is responsible for displaying data to the user and handling user interactions. It corresponds to the Presentation layer in Clean Architecture. The View observes the ViewModel and updates the UI based on the state exposed by the ViewModel.</p>

<h3>3. ViewModel</h3>
<p>The ViewModel acts as a bridge between the Model and the View. It retrieves data from the Model and prepares it for display in the View. The ViewModel contains no reference to the View, making it easier to test. It interacts with the Domain layer (Model) and exposes state to the Presentation layer (View).</p>

<h3>Benefits of Combining MVVM with Clean Architecture</h3>

<h4>Separation of Concerns</h4>
<p>MVVM, in conjunction with Clean Architecture, ensures a clear separation between the UI, business logic, and data handling. This makes the codebase easier to understand, maintain, and extend.</p>

<h4>Testability</h4>
<p>The ViewModel in MVVM can be tested independently of the View, and the Model can be tested independently of the Presentation layer. This isolation of components facilitates comprehensive unit testing.</p>

<h4>Maintainability</h4>
<p>By separating the concerns into distinct layers, changes in one part of the application have minimal impact on other parts. This modularity makes the codebase more maintainable and adaptable to change.</p>

<h4>Scalability</h4>
<p>The combination of MVVM and Clean Architecture allows different teams to work on different layers of the application simultaneously, improving the overall scalability of the development process.</p>


<h1 align="center">App Screenshots : </h1>

<br></br>
<p align="center">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/40744f80-2116-4d58-b1ca-6a7a277c6159" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/bb4c8f64-48a5-4ab4-879d-9ec72004c4c8" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/Abdelrahman-SW/Doctor-Clean-Architecture-App/assets/171629145/d8c7559b-e1a7-49e2-955d-6ef4f8207c04" width="360" height="720">
</p>
<br></br>


<h2 align="left">Conclusion</h2>
This Simple App Is An Implementation To The Clean Arch App Code , in this App I followed The Mvvm design pattern , and i packaged the app code with features and then split each feature package 
by presentation,domain,data layers

And i applied the di (dependency Injection) and concrete interfaces ,

In this way i ensure that my app code is now scalable and can be extend very easily ! 🎉🎉🎉

<br></br>
-- This App Was Developed At 2024 📅
<br></br>
