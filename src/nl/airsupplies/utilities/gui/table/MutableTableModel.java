package nl.airsupplies.utilities.gui.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;

import net.jcip.annotations.Immutable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2005-08-07
public class MutableTableModel extends AbstractTableModel implements List<Object[]> {
	private final List<Object[]> tableData = new ArrayList<>(100);
	private final String[]       tableColumnNames;
	private final boolean[]      editable;

	@SuppressWarnings("OverridableMethodCallDuringObjectConstruction")
	public MutableTableModel(String... tableColumnNames) {
		this.tableColumnNames = tableColumnNames.clone();
		editable              = new boolean[tableColumnNames.length];

		fireTableRowsInserted(0, -1);
	}

	@SuppressWarnings("OverridableMethodCallDuringObjectConstruction")
	public MutableTableModel(String[] tableColumnNames, boolean[] editable) {
		this.tableColumnNames = tableColumnNames.clone();
		this.editable         = editable.clone();

		fireTableRowsInserted(0, -1);
	}

	@SuppressWarnings("OverridableMethodCallDuringObjectConstruction")
	public MutableTableModel(Collection<Object[]> tableData, String[] tableColumnNames, boolean[] editable) {
		this.tableData.addAll(tableData);
		this.tableColumnNames = tableColumnNames.clone();
		this.editable         = editable.clone();

		fireTableRowsInserted(0, tableData.size() - 1);
	}

	@Override
	@SuppressWarnings("OverloadedVarargsMethod")
	public boolean add(Object... elements) {
		int i = size();
		tableData.add(elements);
		fireTableRowsInserted(i, i);
		return true;
	}

	@Override
	@SuppressWarnings("OverloadedVarargsMethod")
	public void add(int index, Object... element) {
		tableData.add(index, element);
		fireTableRowsInserted(index, index);
	}

	@Override
	public boolean addAll(Collection<? extends Object[]> c) {
		int len = c.size();
		if (len != 0) {
			int oldSize = tableData.size();
			tableData.addAll(c);
			fireTableRowsInserted(oldSize, oldSize + len - 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object[]> c) {
		int len = c.size();
		if (len != 0) {
			tableData.addAll(index, c);
			fireTableRowsInserted(index, index + len - 1);
			return true;
		}
		return false;
	}

	@Override
	public Object[] set(int index, Object... element) {
		Object[] out = tableData.set(index, element);
		fireTableRowsUpdated(index, index);
		return out;
	}

	@Override
	public boolean remove(Object o) {
		if (!(o instanceof Object[])) {
			return false;
		}

		int index = tableData.indexOf(o);
		if (index == -1) {
			return false;
		}

		tableData.remove(index);
		fireTableRowsDeleted(index, index);
		return true;
	}

	@Override
	public Object[] remove(int index) {
		Object[] out = tableData.remove(index);
		fireTableRowsDeleted(index, index);
		return out;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (int i = tableData.size() - 1; i >= 0; i--) {
			if (c.contains(tableData.get(i))) {
				tableData.remove(i);
				fireTableRowsDeleted(i, i);
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		for (int i = tableData.size() - 1; i >= 0; i--) {
			if (!c.contains(tableData.get(i))) {
				tableData.remove(i);
				fireTableRowsDeleted(i, i);
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		int oldSize = tableData.size();
		if (oldSize != 0) {
			tableData.clear();
			fireTableRowsDeleted(0, oldSize - 1);
		}
	}

	@Override
	public int size() {
		return tableData.size();
	}

	@Override
	public int getRowCount() {
		return tableData.size();
	}

	@Override
	public int getColumnCount() {
		return tableColumnNames.length;
	}

	@Override
	public int indexOf(Object o) {
		return tableData.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return tableData.lastIndexOf(o);
	}

	@Override
	public boolean contains(Object o) {
		return tableData.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return new HashSet<>(tableData).containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return tableData.isEmpty();
	}

	@Override
	public Object[] get(int index) {
		return tableData.get(index);
	}

	@Override
	public Object getValueAt(int row, int col) {
		requireRange(0, size() - 1, row, "row");
		requireRange(0, tableData.get(row).length - 1, col, "col");

		return tableData.get(row)[col];
	}

	@Override
	public List<Object[]> subList(int fromIndex, int toIndex) {
		return tableData.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return tableData.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return tableData.toArray(a);
	}

	@Override
	public Iterator<Object[]> iterator() {
		return tableData.iterator();
	}

	@Override
	public ListIterator<Object[]> listIterator() {
		return tableData.listIterator();
	}

	@Override
	public ListIterator<Object[]> listIterator(int index) {
		return tableData.listIterator(index);
	}

	@Override
	public String getColumnName(int col) {
		return tableColumnNames[col];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (!isEmpty()) {
			for (Object[] row : tableData) {
				if (row[columnIndex] != null) {
					return row[columnIndex].getClass();
				}
			}
		}

		return super.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return editable[col] &&
		       getValueAt(row, col).getClass().getAnnotation(Immutable.class) == null;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		tableData.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}
}
