package cn.swift.chapter4;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.swift.annotation.ThreadSafe;

/**
 * 4-9 将线程安全性委托给多个状态变量，由于两个变量不存在耦合关系，所以是线程安全的
 */
@ThreadSafe
public class VisualComponent {

    private final CopyOnWriteArrayList<KeyListener> keyListeners = new CopyOnWriteArrayList<>();
    
    private final CopyOnWriteArrayList<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();
    
    public void addKeyListener(KeyListener kl) {
	keyListeners.add(kl);
    }
    
    public void addMouseListener(MouseListener ml) {
	mouseListeners.add(ml);
    }
    
    public void removeKeyListener(KeyListener kl) {
	keyListeners.remove(kl);
    }
    
    public void removeMouseListener(MouseListener ml) {
	mouseListeners.remove(ml);
    }
}
