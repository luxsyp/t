# Transferwise

## Project structure

The project has 3 modules:

- app: create and handle the dependency injections
- dependency: expose the main interfaces that will be necessary as we add features to the project   
- rickandmorty: feature module that contains a list / details of Rick and Morty characters

## Architecture

Clean architecture with clear separation of roles:
- interactor / repository / presenter / view 

## Offline 

In order to enable the app to work offline I used Room library.
Services are delegated to a cached version of themselves which check the database first, then request through network.

## Design

I had the projet be design-less to let the focus to be on the code.

## 3rd party libraries

- Retrofit
- Gson
- Picasso
- Lifecycle
- Room
- Supports / recyclerview / cardview ...
- Junit / Mockito / assertJ