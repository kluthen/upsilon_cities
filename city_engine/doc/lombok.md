# TECH DOC LOMBOK

This is a nifty tool to remove a few LOCs, mostly getter, setter, toString and some constructors. 

## Installation

Oddly, this is something that took some time for me to find, whereas it was readily accessible from the (website)[https://projectlombok.org/setup/maven]

> In a few word, add the dependency to *pom.xml*
> Add the annotation processor ( the stuff that will enact the annotations ) 
> And voila!

## Usage

The simplest way to use it is to annotate the class with **@Data** (don't forget to *import lombok.Data* with **a capital D**)

This will add all getter and setters, a mandatory constructor for final props and clean toString. (See)[https://projectlombok.org/features/Data]