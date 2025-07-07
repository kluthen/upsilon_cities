# Data

Contains definitions of Data classes.

## DTO

DTO stands for **data transfert object.** These classes are reductions of a true Data Class, and is mostly used to prevent leakage of internal or under UAC information.

All DTO should inherit from DTO, and most related services must be able to retrieve from a DTO the original object with a **static public T fromDTO(Long id)** method.

Note: **@EqualsAndHashCode(callSuper=false)** is added to remove a warning.
Note: Lombok doesn't deal with extends... so we have to do it ourselve.