package org.springframework.samples.petclinic.amazingco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.Personnel;

/**
 * Simple JavaBean domain object representing an employee.
 *
 * @author Jason deMello
 */
@Entity
@Table(name = "employees")
public class Employee extends Personnel {

}
