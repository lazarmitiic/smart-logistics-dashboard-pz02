import React, { useState, useEffect } from 'react';
import api from './api';
import './App.css';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [user, setUser] = useState(null);
  const [usernameInput, setUsernameInput] = useState('');
  const [passwordInput, setPasswordInput] = useState('');
  const [loginError, setLoginError] = useState('');

  const [shipments, setShipments] = useState([]);
  const [statusFilter, setStatusFilter] = useState('');
  const [priorityFilter, setPriorityFilter] = useState('');

  const [formData, setFormData] = useState({
    trackingCode: '',
    description: '',
    status: 'Pending',
    priority: 'Low',
    weight: '',
  });

  const [toastMessage, setToastMessage] = useState('');

  useEffect(() => {
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        setUser({
          username: payload.sub,
          role: payload.role,
        });
        localStorage.setItem('token', token);
      } catch (e) {
        handleLogout();
      }
    }
  }, [token]);

  useEffect(() => {
    if (token && user) {
      fetchShipments();
    }
  }, [token, user, statusFilter, priorityFilter]);

  const showToast = (msg) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(''), 3000);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoginError('');
    try {
      const response = await api.post('/api/auth/login', {
        username: usernameInput,
        password: passwordInput,
      });
      setToken(response.data.token);
    } catch (err) {
      setLoginError('Invalid username or password');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken('');
    setUser(null);
    setShipments([]);
  };

  const fetchShipments = async () => {
    try {
      const params = {};
      if (statusFilter) params.status = statusFilter;
      if (priorityFilter) params.priority = priorityFilter;
      const response = await api.get('/api/shipments', { params });
      setShipments(response.data);
    } catch (err) {
      showToast('Error fetching shipments');
    }
  };

  const handleCreateShipment = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        weight: parseFloat(formData.weight) || 0.0,
      };
      await api.post('/api/shipments', payload);
      showToast('Shipment created successfully');
      setFormData({
        trackingCode: '',
        description: '',
        status: 'Pending',
        priority: 'Low',
        weight: '',
      });
      fetchShipments();
    } catch (err) {
      showToast('Only ADMIN users can create/modify shipments');
    }
  };

  const handleDeleteShipment = async (id) => {
    try {
      await api.delete(`/api/shipments/${id}`);
      showToast('Shipment deleted successfully');
      fetchShipments();
    } catch (err) {
      showToast('Only ADMIN users can delete shipments');
    }
  };

  const getStatusClass = (status) => {
    switch (status?.toLowerCase()) {
      case 'in transit':
        return 'status-badge-custom transit';
      case 'delivered':
        return 'status-badge-custom delivered';
      default:
        return 'status-badge-custom pending';
    }
  };

  const getPriorityClass = (priority) => {
    switch (priority?.toLowerCase()) {
      case 'critical':
        return 'priority-badge-custom critical';
      case 'high':
        return 'priority-badge-custom high';
      case 'medium':
        return 'priority-badge-custom medium';
      default:
        return 'priority-badge-custom low';
    }
  };

  const totalCount = shipments.length;
  const pendingCount = shipments.filter(s => s.status?.toLowerCase() === 'pending').length;
  const transitCount = shipments.filter(s => s.status?.toLowerCase() === 'in transit').length;
  const deliveredCount = shipments.filter(s => s.status?.toLowerCase() === 'delivered').length;

  if (!token || !user) {
    return (
      <div className="app-wrapper">
        <div className="login-container">
          <div className="login-box">
            <div className="login-logo">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2.5} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M15 19.128a9.38 9.38 0 002.625.372 9.337 9.337 0 004.121-.952 4.125 4.125 0 00-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.109A11.978 11.978 0 0112 20.25a11.978 11.978 0 01-3-1.013v-.109m0 0a9.38 9.38 0 012.625-.372 9.337 9.337 0 014.121-.952 4.125 4.125 0 00-7.533-2.493M9 19.128v-.003c0-1.113.285-2.16.786-3.07M9 19.128v.109A11.978 11.978 0 016 20.25a11.978 11.978 0 01-3-1.013v-.109m0 0A9.337 9.337 0 013 18.26c0-1.285.397-2.482 1.077-3.477M6 20.25a11.978 11.978 0 01-3-1.013v-.109m0 0a9.337 9.337 0 013-1.013v.109z" />
              </svg>
            </div>
            <h1 className="login-heading">Smart Logistics</h1>
            <p className="login-desc">Sign in to manage shipments and tracks</p>
            <form onSubmit={handleLogin}>
              <div className="form-field">
                <label className="form-label-custom">Username</label>
                <input
                  type="text"
                  className="form-input-custom"
                  value={usernameInput}
                  onChange={(e) => setUsernameInput(e.target.value)}
                  required
                  placeholder="Enter username"
                />
              </div>
              <div className="form-field">
                <label className="form-label-custom">Password</label>
                <input
                  type="password"
                  className="form-input-custom"
                  value={passwordInput}
                  onChange={(e) => setPasswordInput(e.target.value)}
                  required
                  placeholder="Enter password"
                />
              </div>
              {loginError && (
                <div className="error-message">
                  {loginError}
                </div>
              )}
              <button type="submit" className="login-btn-custom">Sign In</button>
            </form>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="app-wrapper">
      <nav className="nav-container">
        <div className="nav-content">
          <div className="logo-section">
            <div className="logo-icon">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2.5} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 18.75a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h6m-9 0H3.375a1.125 1.125 0 01-1.125-1.125V14.25m17.25 4.5a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h1.125c.621 0 1.129-.504 1.129-1.125V11.25M12 18.75c-3.75 0-6-3-6-3m6 3c3.75 0 6-3 6-3m-6-3a3 3 0 11-6 0 3 3 0 016 0zm6 0a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </div>
            <span className="logo-text">SMART LOGISTICS</span>
          </div>
          <div className="user-profile">
            <div className="user-info">
              <span className="user-name">{user.username}</span>
              <span className="user-role-badge">{user.role}</span>
            </div>
            <button onClick={handleLogout} className="signout-button">Sign Out</button>
          </div>
        </div>
      </nav>

      <main className="main-layout">
        <div className="dashboard-overview">
          <div className="overview-card total">
            <div className="card-icon-wrapper">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M20.25 7.5l-.625 10.632a2.25 2.25 0 01-2.247 2.118H6.622a2.25 2.25 0 01-2.247-2.118L3.75 7.5M10 11.25h4M3.375 7.5h17.25c.621 0 1.125-.504 1.125-1.125v-1.5c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125z" />
              </svg>
            </div>
            <div className="card-details">
              <span className="card-count">{totalCount}</span>
              <span className="card-label">Total Shipments</span>
            </div>
          </div>

          <div className="overview-card pending">
            <div className="card-icon-wrapper">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div className="card-details">
              <span className="card-count">{pendingCount}</span>
              <span className="card-label">Pending Status</span>
            </div>
          </div>

          <div className="overview-card transit">
            <div className="card-icon-wrapper">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 18.75a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h6m-9 0H3.375a1.125 1.125 0 01-1.125-1.125V14.25m17.25 4.5a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h1.125c.621 0 1.129-.504 1.129-1.125V11.25M12 18.75c-3.75 0-6-3-6-3m6 3c3.75 0 6-3 6-3m-6-3a3 3 0 11-6 0 3 3 0 016 0zm6 0a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </div>
            <div className="card-details">
              <span className="card-count">{transitCount}</span>
              <span className="card-label">In Transit</span>
            </div>
          </div>

          <div className="overview-card delivered">
            <div className="card-icon-wrapper">
              <svg fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75L11.25 15 15 9.75M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div className="card-details">
              <span className="card-count">{deliveredCount}</span>
              <span className="card-label">Delivered</span>
            </div>
          </div>
        </div>

        <div className="content-grid">
          <div className="dashboard-panel">
            <div className="panel-header-custom">
              <h2 className="panel-title-custom">Shipments Registry</h2>
              <div className="filter-section">
                <select
                  className="filter-select"
                  value={statusFilter}
                  onChange={(e) => setStatusFilter(e.target.value)}
                >
                  <option value="">All Statuses</option>
                  <option value="Pending">Pending</option>
                  <option value="In Transit">In Transit</option>
                  <option value="Delivered">Delivered</option>
                </select>

                <select
                  className="filter-select"
                  value={priorityFilter}
                  onChange={(e) => setPriorityFilter(e.target.value)}
                >
                  <option value="">All Priorities</option>
                  <option value="Low">Low</option>
                  <option value="Medium">Medium</option>
                  <option value="High">High</option>
                  <option value="Critical">Critical</option>
                </select>
              </div>
            </div>

            <div className="table-scroller">
              <table className="logistics-table">
                <thead>
                  <tr>
                    <th>Tracking Code</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Weight</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {shipments.map((shipment) => (
                    <tr key={shipment.id}>
                      <td>
                        <span className="tracking-badge">{shipment.trackingCode}</span>
                      </td>
                      <td style={{ fontWeight: 500 }}>{shipment.description}</td>
                      <td>
                        <span className={getStatusClass(shipment.status)}>{shipment.status}</span>
                      </td>
                      <td>
                        <span className={getPriorityClass(shipment.priority)}>{shipment.priority}</span>
                      </td>
                      <td style={{ fontWeight: 600 }}>{shipment.weight} kg</td>
                      <td>
                        <button
                          onClick={() => handleDeleteShipment(shipment.id)}
                          className="action-btn"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="dashboard-panel">
            <h2 className="panel-title-custom" style={{ marginBottom: '1.5rem' }}>New Shipment</h2>
            <form onSubmit={handleCreateShipment}>
              <div className="form-group-custom">
                <label>Tracking Code</label>
                <input
                  type="text"
                  className="form-input-custom"
                  value={formData.trackingCode}
                  onChange={(e) => setFormData({ ...formData, trackingCode: e.target.value })}
                  required
                  placeholder="TRK-XXXXX"
                />
              </div>
              <div className="form-group-custom">
                <label>Description</label>
                <input
                  type="text"
                  className="form-input-custom"
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                  required
                  placeholder="Cargo description"
                />
              </div>
              <div className="form-group-custom">
                <label>Status</label>
                <select
                  className="form-select-custom"
                  value={formData.status}
                  onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                >
                  <option value="Pending">Pending</option>
                  <option value="In Transit">In Transit</option>
                  <option value="Delivered">Delivered</option>
                </select>
              </div>
              <div className="form-group-custom">
                <label>Priority</label>
                <select
                  className="form-select-custom"
                  value={formData.priority}
                  onChange={(e) => setFormData({ ...formData, priority: e.target.value })}
                >
                  <option value="Low">Low</option>
                  <option value="Medium">Medium</option>
                  <option value="High">High</option>
                  <option value="Critical">Critical</option>
                </select>
              </div>
              <div className="form-group-custom">
                <label>Weight (kg)</label>
                <input
                  type="number"
                  step="0.1"
                  className="form-input-custom"
                  value={formData.weight}
                  onChange={(e) => setFormData({ ...formData, weight: e.target.value })}
                  required
                  placeholder="0.0"
                />
              </div>
              <button type="submit" className="submit-btn-custom">Create Shipment</button>
            </form>
          </div>
        </div>
      </main>

      {toastMessage && (
        <div className="toast-custom">
          <span>{toastMessage}</span>
        </div>
      )}
    </div>
  );
}

export default App;
