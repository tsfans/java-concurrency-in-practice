package cn.swift.chapter3;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 3-9 基本类型的局部变量与引用变量的线程封闭性
 */
public class StackSeal {

    public int loadTheArk(Collection<Animal> candidates) {
	SortedSet<Animal> animals;
	int numPairs = 0;
	Animal candidate = null;

	// animals被封闭在方法，不要使其逸出
	animals = new TreeSet<Animal>(new SpeciesGenderComparator());
	animals.addAll(candidates);
	for (Animal a : animals) {
	    if (candidate == null || candidate.isPotentialMate(a)) {
		candidate = a;
	    } else {
		++numPairs;
		candidate = null;
	    }
	}
	return numPairs;
    }

    class Animal {
	public int gender() {
	    return 1;
	}

	public boolean isPotentialMate(Animal a) {
	    return a.gender() != gender();
	}
    }

    class SpeciesGenderComparator implements Comparator<Animal> {

	@Override
	public int compare(Animal o1, Animal o2) {
	    return o1.gender() - o2.gender();
	}

    }
}
