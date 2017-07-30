
public class HashTableWithChaining<E> {
    final static double LOAD_FACTOR = 0.75;
    private int size = 0;
    private Node<E>[] nodes;


    public HashTableWithChaining(int initialLength) {
        nodes = new Node[initialLength];
    }

    public void add(E obj) {
        if (needsExpanding()) {
            expandCapacity();
        }

        if (!contains(obj)) {
            if (nodes[obj.hashCode() % nodes.length] == null) {
                nodes[obj.hashCode() % nodes.length] = new Node<>(obj);
            } else {
                Node currentNode = nodes[obj.hashCode() % nodes.length];
                while (currentNode.nextNode != null) {
                    currentNode = currentNode.nextNode;
                }
                currentNode.nextNode = new Node<>(obj);
            }
            size++;
        }
    }

    public void remove(E obj) {
        if (contains(obj)) {
            Node node = nodes[obj.hashCode() % nodes.length];
            if (node.element.equals(obj)) {
                nodes[obj.hashCode() % nodes.length] = node.nextNode;
                size--;
            } else {
                while (node.nextNode != null && !node.nextNode.element.equals(obj)) {
                    node = node.nextNode;
                }
                if (node.nextNode != null && node.nextNode.element.equals(obj)) {
                    node.nextNode = node.nextNode.nextNode;
                    size--;
                }
            }
        }
    }

    public boolean contains(E obj) {
        Node node = nodes[obj.hashCode() % nodes.length];
        boolean found = false;
        if (node != null) {
            if (node.element.equals(obj)) {
                found = true;
            } else {
                while (node.nextNode != null && !found) {
                    node = node.nextNode;
                    if (node.element.equals(obj)) {
                        found = true;
                    }
                }
            }
        }
        return found;
    }

    public int size() {
        return size;
    }

    public boolean needsExpanding() {
        return  (double)size() / (double)nodes.length >= LOAD_FACTOR;
    }

    public void expandCapacity() {
        Node<E>[] newHashTable = (Node<E>[]) new Node[nodes.length * 2];

        for (Node<E> node : nodes) {
            while(node != null) {
                if (node != null) {
                    Node newNode = newHashTable[node.element.hashCode() % newHashTable.length];
                    if (newNode == null) {
                        newHashTable[node.element.hashCode() % newHashTable.length] = new Node(node.element);
                    } else {
                        while (newNode.nextNode != null) {
                            newNode = newNode.nextNode;
                        }
                        newNode.nextNode = new Node(node.element);
                    }
                }
                node = node.nextNode;
            }
        }
        nodes = newHashTable;
    }

    public class Node<E> {
        E element;
        Node<E> nextNode;

        public Node(E element) {
            this.element = element;
            nextNode = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nodes.length; i++) {
            Node currentNode = nodes[i];
            sb.append("Index ").append(i).append(": ");

            if (nodes[i] == null) {
                sb.append("[null]");
            } else {
                sb.append("[").append(nodes[i].element).append("]");
                while (currentNode.nextNode != null) {
                    currentNode = currentNode.nextNode;
                    sb.append(", [").append(currentNode.element).append("]");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("====== HashTableWithChaining ======");
        HashTableWithChaining<Person> hashTable = new HashTableWithChaining<>(5);

        System.out.println("Adding: Jack, Jill, Bob");
        Person jill = new Person("Jill", "", 18);
        Person bob = new Person("Bob", "", 28);
        Person jack = new Person("Jack", "", 21);
        hashTable.add(jack);
        hashTable.add(bob);
        hashTable.add(jill);
        hashTable.printHashTable();

        System.out.println("Adding: Mary");
        hashTable.add(new Person("Mary", "", 50));
        hashTable.printHashTable();

        System.out.println("Adding: James, Winston, Ronald");
        Person james = new Person("James", "", 20);
        Person winston = new Person("Winston", "", 35);
        hashTable.add(james);
        hashTable.add(winston);
        hashTable.add(new Person("Ronald", "", 42));
        hashTable.printHashTable();

        System.out.println("Removing Non-Existing Person: Matt");
        hashTable.remove(new Person("Matt", "", 0));
        hashTable.printHashTable();

        System.out.println("HashTable contains Matt : " +
                hashTable.contains(new Person("Matt", "", 0)));
        System.out.println("HashTable contains James : "
                + hashTable.contains(james));
        System.out.println("HashTable contains Winston : "
                + hashTable.contains(winston));
        System.out.println("--------------------------------------");

        System.out.println("Removing: James, Winston, Jill");
        hashTable.remove(james);
        hashTable.remove(winston);
        hashTable.remove(jill);
        hashTable.printHashTable();

        System.out.println("Adding: Jill");
        hashTable.add(jill);
        hashTable.printHashTable();

        System.out.println("Removing: Bob");
        hashTable.remove(bob);
        hashTable.printHashTable();
    }

    private void printHashTable() {
        System.out.println("Current Load: " + ((double)size()/(double)nodes.length) * 100 + "%");
        System.out.println("Current HashTable: ");
        System.out.println(this);
        System.out.println("--------------------------------------");
    }

}
