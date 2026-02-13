# Phone Directory

Design a phone directory system.

## Problem Statement

Implement a phone directory that supports adding, searching, updating, and deleting
contacts with prefix-based search (autocomplete) and reverse phone lookup.

### Requirements

- Add contacts (name, phone number, email)
- Exact name lookup
- Prefix-based search / autocomplete
- Reverse lookup by phone number
- Delete contacts
- Case-insensitive name operations
- All contacts listed alphabetically

## Class Diagram

```
Contact (implements Comparable)
├── String name, phoneNumber, email
├── compareTo() — alphabetical by name

TrieNode
├── Map<Character, TrieNode> children
├── Contact contact (at leaf)

PhoneDirectory
├── TrieNode root             — Trie for name-based search
├── Map<String, Contact> phoneIndex — reverse lookup by phone
├── addContact(Contact)
├── findContact(name) → Contact
├── findByPhoneNumber(phone) → Contact
├── deleteContact(name) → boolean
├── searchByPrefix(prefix) → List<Contact>
├── allContacts() → List<Contact>
```

## Design Benefits

✅ O(L) name lookup/search via Trie  
✅ O(1) reverse phone lookup via HashMap  
✅ Autocomplete built into prefix search  
✅ Sorted output for contact listing  
